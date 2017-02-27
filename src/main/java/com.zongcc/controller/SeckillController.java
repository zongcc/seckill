package com.zongcc.controller;

import com.zongcc.dto.Exposer;
import com.zongcc.dto.SeckillExecution;
import com.zongcc.dto.SeckillResult;
import com.zongcc.enums.SeckillStateEnum;
import com.zongcc.exception.RepeatKillException;
import com.zongcc.exception.SeckillCloseException;
import com.zongcc.model.Seckill;
import com.zongcc.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by chunchengzong on 2016-09-21.
 */
@Controller
@RequestMapping("/kill")
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SeckillService seckillService;

    /**
     * 获取秒杀列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<Seckill> seckillList = seckillService.getSeckillList(0, 10);
        modelAndView.addObject("list", seckillList);
        modelAndView.setViewName("list");
        model.addAttribute("list",seckillList);
        return "list";//web-inf/jsp/list.jsp
    }

    /**
     * 秒杀详情页
     *
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "/detail/{seckillId}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(value = "seckillId")Long seckillId) {
        ModelAndView modelAndView = new ModelAndView();
        if (null == seckillId) {
            return new ModelAndView("redirect:/seckill/list");
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (null == seckill) {
            return new ModelAndView("forward:/seckill/list");
        }
        modelAndView.addObject("seckill", seckill);
        modelAndView.setViewName("detail");
        return modelAndView;
    }

    /**
     * 暴露秒杀接口
     *
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable(value = "seckillId") Long seckillId) {
        SeckillResult<Exposer> result = null;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    /**
     * 执行秒杀
     *
     * @param seckillId
     * @param phone
     * @param md5
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execute", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @CookieValue(value = "killPhone", required = false) Long phone,
                                                   @PathVariable("md5") String md5) {
        if (null == phone) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        SeckillResult<SeckillExecution> result = null;
        SeckillExecution seckillExecution = null;
        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
            result = new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatKillException e) {
            seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.SECKILL_REPEAT);
            result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (SeckillCloseException e) {
            seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.SECKILL_END);
            result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.SECKILL_ERROR);
            result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        }
        return result;
    }

    /**
     * 获取服务器当前时间
     *
     * @return
     */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }

}
