package com.zongcc.ansj;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.List;

/**
 * Created by chunchengzong on 2017-08-04.
 */
public class ANSJ {
    public static void main(String[] args) {
        String str = "教育孩子问题是父母面临最大的挑战";
        System.out.println(ToAnalysis.parse(str));
        System.out.println(HanLP.segment(str));

        //标准分词 HanLP
        List<Term> termList = StandardTokenizer.segment("中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程");
        System.out.println(termList);

        //NLP分词
        List<Term> termList2 = NLPTokenizer.segment("中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程");
        System.out.println(termList2);

        //索引分词
        List<Term> termList3 = IndexTokenizer.segment("中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程");
        for (Term term : termList3)
        {
            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
        }

        //N-最短路径分词
        Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        String[] testCase = new String[]{
                "今天，刘志军案的关键人物,山西女商人丁书苗在市二中院出庭受审。",
                "刘喜杰石国祥会见吴亚琴先进事迹报告团成员",
        };
        for (String sentence : testCase)
        {
            System.out.println("N-最短分词：" + nShortSegment.seg(sentence) + "\n最短路分词：" + shortestSegment.seg(sentence));
        }
    }
}
