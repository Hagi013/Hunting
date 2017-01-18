package jp.co.hands.hunting.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
