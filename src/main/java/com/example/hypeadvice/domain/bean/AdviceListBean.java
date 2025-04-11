package com.example.hypeadvice.domain.bean;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.service.AdviceService;
import com.example.hypeadvice.domain.vo.AdviceListVO;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AdviceListBean extends Bean {

    @Autowired AdviceService adviceService;

    private Advice advice = new Advice();
    private AdviceListVO adviceListVO;

    public void initBean() {
        advice = new Advice();
    }

    public void buscar() {
        try {
            this.adviceListVO = adviceService.buscar(advice);
        } catch (Exception e) {
            addMessageError(e);
        }
    }

    public AdviceListVO getAdviceListVO() {
        return adviceListVO;
    }

    public void setAdviceListVO(AdviceListVO adviceListVO) {
        this.adviceListVO = adviceListVO;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
