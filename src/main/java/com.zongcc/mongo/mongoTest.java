package com.zongcc.mongo;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Updates.inc;

/**
 * Created by zongcc on 2016/12/20.
 */
public class mongoTest {
    public static void main(String [] args) {
        try{
            // 连接到 mongodb 服务
            //MongoClient mongoClient = new MongoClient();//默认是127.0.0.1:27017
            //MongoClient mongoClient = new MongoClient("127.0.0.1");
            //MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );
            //MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));//这里可以使用多个

            ServerAddress serverAddress = new ServerAddress("127.0.0.1" , 27017);
            MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
                    .maxWaitTime(1000).writeConcern(WriteConcern.UNACKNOWLEDGED).build();
            MongoClient mongoClient = new MongoClient(serverAddress,mongoClientOptions);
            mongoClient.getWriteConcern();


            // 连接到数据库
            MongoDatabase database = mongoClient.getDatabase("zongcc");
            //链接collections
            MongoCollection<Document> collection = database.getCollection("person");
            collection.drop();
            //创建一个限制大小的collcetion
            //database.createCollection("zcc",new CreateCollectionOptions().capped(true).sizeInBytes(0x100000));

            MongoIterable<String> listCollectionNames = database.listCollectionNames();
            for (String name:listCollectionNames){
                System.out.println("collection名称为："+name);
            }

            //插入单条数据
            Document doc = new Document("name", "MongoDB")
                    .append("type", "database")
                    .append("count", 100)
                    .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"));
            collection.insertOne(doc);

            //插入多条数据
            List<Document> list = new ArrayList<Document>();
            for(int i=0;i<100;i++){
                list.add(new Document("name", "MongoDB")
                        .append("type", "database")
                        .append("count", i)
                        .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6")));
            }
            collection.insertMany(list);

            //查询collections多少数据
            System.out.println("当前collections中存在数据个数为："+collection.count());

            //查询collections第一条数据
            Document first = collection.find().first();
            System.out.println("查询collections第一条数据:"+first.toJson());


            //查询colletions所有数据列表
            FindIterable<Document> documents = collection.find();
            MongoCursor<Document> iterator = documents.iterator();
            while (iterator.hasNext()){
                System.out.println("---"+iterator.next());
            }

            //设置query查询条件取出第一条
            Document myDoc = new Document();
           /* myDoc.put("i",73);
            myDoc = collection.find(myDoc).first();*/
            myDoc = collection.find(Filters.eq("count", 1)).first();
            System.out.println("设置query查询条件取出第一条:"+myDoc.toJson());

            //设置query查询条件取出所有
            Block<Document> printBlock = new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    System.out.println("==="+document.toJson());
                }
            };
            collection.find(Filters.gt("count", 90)).forEach(printBlock);
            System.out.println("====================================================================");
            collection.find(Filters.and(gt("count", 0), Filters.lte("count", 10))).forEach(printBlock);
            System.out.println("====================================================================");
            collection.find(new Document("count", new Document("$gte", 98))).forEach(printBlock);
            System.out.println("====================================================================");
            collection.find().projection(new Document("name", 1).append("type", 1).append("count",1).append("_id", 0))
                    .forEach(printBlock);//指定选出那些字段
            System.out.println("====================================================================");
            collection.find().projection(Projections.fields(Projections.include("name", "type", "versions"), Projections.excludeId()))
                    .forEach(printBlock);
            System.out.println("====================================================================");
            collection.find().sort(Sorts.ascending("count"))
                    .projection(Projections.fields(Projections.include("name","count", "type")))
                    .forEach(printBlock);
            System.out.println("====================================================================");
            //更新collections数据
            collection.updateOne(Filters.eq("count", 10), new Document("$set", new Document("count", 110)));
            UpdateResult updateResult = collection.updateMany(lt("count", 100), inc("count", 100));
            collection.updateOne(
                    Filters.eq("count", 110),
                    Updates.combine(Updates.set("name", "Fresh Breads and Tulips"), Updates.currentDate("lastModified")),
                    new UpdateOptions().upsert(true).bypassDocumentValidation(true));
            collection.replaceOne(
                    eq("name", "Fresh Breads and Tulips"),
                    new Document("stars", 5)
                            .append("contact", "TBD")
                            .append("categories", Arrays.asList("Cafe", "Pastries", "Ice Cream")),
                    new UpdateOptions().upsert(true).bypassDocumentValidation(true));
            System.out.println("更新了数据条数："+updateResult.getModifiedCount());

            //删除collections数据
            collection.deleteOne(eq("count", 111));
            DeleteResult deleteResult = collection.deleteMany(gte("count", 200));
            System.out.println("删除数据条数："+deleteResult.getDeletedCount());

            //创建索引
            // For an ascending index type, specify 1 for <type>.
            // For a descending index type, specify -1 for <type>.
            //collection.createIndex(new Document("i", 1));
            //collection.createIndex(Indexes.ascending("name","type"));
            //collection.createIndex(Indexes.descending("count","versions"));
            collection.createIndex(Indexes.compoundIndex(Indexes.descending("name"), Indexes.ascending("count")));
            //唯一索引
            /*IndexOptions indexOptions = new IndexOptions().unique(true);
            collection.createIndex(Indexes.ascending("name", "stars"), indexOptions);*/
            for (Document index : collection.listIndexes()) {
                System.out.println("索引名称："+index.toJson());
            }

            collection.drop();
            System.out.println("Connect to database successfully");

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
