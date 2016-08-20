package jp.co.hands.hunting.controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import jp.co.hands.hunting.application.helper.JsfManagedObjectFetcher;
import jp.co.hands.hunting.entity.model.BaseEntity;
import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.repository.JpaDaoSupport;

public class BaseController implements Serializable {

	
	/**
    * FacesMessageをContextへ追加します。
    * @param serverity メッセージレベル
    * @param summary メッセージサマリ
    * @param detail メッセージ詳細
    */	
	protected void addMessage(FacesMessage.Severity errorLevel, String summary, String detail) {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage("", new FacesMessage(errorLevel, summary, detail));
	}	
	
    /**
     * 指定されたページへのリダイレクト用文字列を取得します。
     * @param path リダイレクト先ページ
     * @return リダイレクト用文字列
     */
	protected String redirectTo(String path) {
		return path + "?faces-redirect=true";
	}
	
}
