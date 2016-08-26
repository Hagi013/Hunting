package jp.co.hands.hunting.helper;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public final class JsfManagedObjectFetcher {

	/**
	 *JSFが持つオブジェクトの取得
	 *entitiyClass:	取得したいObjectのclass名
	 *object:		JSFで(XHTMLで指定した)オブジェクトの名称
	 *使用例:		pageObjectFetcher.<Product> fetchObject(Product.class, "product");		
	 */
	public static <E extends Serializable> E getObject(Class<E> targetEntity, String entityName) {
	
		/* FacesContextは、JSFアプリケーションの現在の状態を保持しているオブジェクトです */
		FacesContext con = FacesContext.getCurrentInstance();
		
		/*
		 * ExternalContextは、ServletやPortletからJSFを利用することを（特に）意識しているクラスです。
		 * requestやsessionスコープの中で管理されているオブジェクトを取り出すことができます。
		 */
		ExternalContext exCon = con.getExternalContext();
		
		Map map = exCon.getRequestMap();
		return (E) map.get(entityName);
	}
	
	/**
	 *JSFが持つStringオブジェクトの取得
	 *entitiyName:	取得したいJSF上での名前
	 *JSF上だと...（画像のレンダリングの場合）
	 *　			<p:graphicImage value="#{modelListController.convertPic}">
	　*				<f:param name="igModelId" value="#{model.userId}" />
	　* 			</p:graphicImage>
	 *使用例:		pageObjectFetcher.getString("igModelId");		
	 */
	public static String getString(String entityName) {
		
		FacesContext con = FacesContext.getCurrentInstance();
		ExternalContext exCon = con.getExternalContext();
		Map<String, String> map = exCon.getRequestParameterMap();
		
		return map.get(entityName);
	}
	
}
