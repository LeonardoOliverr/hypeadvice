package com.example.hypeadvice.domain.bean;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.service.AdviceService;
import com.example.hypeadvice.domain.vo.AdviceListVO;
import com.example.hypeadvice.domain.vo.Slip;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collections;

@Named
@ViewScoped
public class AdviceListBean extends Bean implements Serializable {

    @Autowired
    private AdviceService adviceService;

    private Advice advice = new Advice();
    private AdviceListVO adviceListVO;
    private Long idBusca;
    private Advice conselhoPorId;

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

    public void buscarPorId() {
        this.adviceListVO = new AdviceListVO(); // Limpa antes

        try {
            Advice conselho = adviceService.buscarPorId(idBusca.intValue());

            if (conselho == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum conselho encontrado para o ID informado.", null));
                return;
            }

            Slip slip = new Slip();
            slip.setId(conselho.getId());
            slip.setAdvice(conselho.getDescricao());

            AdviceListVO listVO = new AdviceListVO();
            listVO.setSlips(Collections.singletonList(slip));
            this.adviceListVO = listVO;

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Conselho encontrado com sucesso!", null));

        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("Advice slip not found")) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Nenhum conselho encontrado para o ID informado.", null));
            } else {
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar conselho por ID.", null));
            }
        }
    }

    // Getters e Setters
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public AdviceListVO getAdviceListVO() {
        return adviceListVO;
    }

    public void setAdviceListVO(AdviceListVO adviceListVO) {
        this.adviceListVO = adviceListVO;
    }

    public Long getIdBusca() {
        return idBusca;
    }

    public void setIdBusca(Long idBusca) {
        this.idBusca = idBusca;
    }

    public Advice getConselhoPorId() {
        return conselhoPorId;
    }

    public void setConselhoPorId(Advice conselhoPorId) {
        this.conselhoPorId = conselhoPorId;
    }
}
