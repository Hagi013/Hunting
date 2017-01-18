package jp.co.hands.hunting.manage.domain.model;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;

import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.helper.FileHundler;
import jp.co.hands.hunting.helper.YamlHelper;
import jp.co.hands.hunting.repository.impl.HuntingModelRepository;
import jp.co.hands.hunting.utils.HuntingUserImg;
import jp.co.hands.hunting.utils.filename.YamlUtils;

@Stateless
public class AdminModelRegisterBl {
	
	@Inject
	private HuntingModelRepository huntingModelRepository;
	
	
	public void registerModel(HuntingModel huntingModel, Part uploadedFile) {
		
		HuntingModel targetModel = huntingModel;
		
		FacesContext fc = FacesContext.getCurrentInstance();
		
		System.out.println("huntingModel.getUserId()" + huntingModel.getUserId());
		System.out.println("huntingModel.getProfilePictureUrl()" + huntingModel.getProfilePictureUrl());
		System.out.println("huntingModel.getUserId().isEmpty()"+ huntingModel.getUserId().isEmpty());
		System.out.println("huntingModel.getUserId() == null"+ huntingModel.getUserId() == null);
		System.out.println("huntingModel.getProfilePictureUrl().isEmpty()"+ huntingModel.getProfilePictureUrl().isEmpty());

		
		if (huntingModel.getUserId() == null || huntingModel.getUserId().isEmpty()) {
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "UserIdを入力してください。"));
			return;
		}
		
		// 画像をURLから取得する
		if(Optional.ofNullable(huntingModel.getProfilePictureUrl()).isPresent() && 
				!huntingModel.getProfilePictureUrl().isEmpty()) {		
			YamlHelper<HuntingUserImg> yh = new YamlHelper();			
			try {
				String path = yh.getYamlInfo(HuntingUserImg.class, YamlUtils.HUNTING_MODEL_IMG_PATH).getImgPath();
				String url = huntingModel.getProfilePictureUrl();
				byte[] imgByte = FileHundler.imageConvFromWeb(url);
				String imgName = System.currentTimeMillis()+"_"+huntingModel.getUserId()+url.substring(url.lastIndexOf("."));
				FileHundler.writeByteToImageFile(path + imgName, imgByte);
				huntingModel.setProfilePictureUrl(imgName);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		// 画像をファイルから取得する		
		if (Optional.ofNullable(uploadedFile).isPresent()) {
			YamlHelper<HuntingUserImg> yh = new YamlHelper();
			try {
				String path = yh.getYamlInfo(HuntingUserImg.class, YamlUtils.HUNTING_MODEL_IMG_PATH).getImgPath();
				String name = uploadedFile.getSubmittedFileName();
				byte[] imgByte = FileHundler.fileConvToByte(uploadedFile);
				String imgName = System.currentTimeMillis()+"_"+huntingModel.getUserId()+name.substring(name.lastIndexOf("."));
				FileHundler.writeByteToImageFile(path + imgName, imgByte);
				targetModel.setProfilePictureUrl(imgName);			

			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		// 新規同ＩＤにてデータが登録されていた場合
		if (!Optional.ofNullable(huntingModelRepository.findByKey(targetModel.getUserId())).isPresent()) {

			// 表示名が入力されていなければ入力してもらう。
			if (targetModel.getDisplayName() == null || targetModel.getDisplayName().isEmpty()) {
				fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "表示名を入力してください。"));
				return;
			}
			huntingModelRepository.save(targetModel, targetModel.getUserId());
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "正常に登録が完了しました。"));
		} else {
			// すでにIDが登録されていた場合
			huntingModelRepository.updata(targetModel, targetModel.getUserId());
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "データの更新が完了しました。"));
		}
		
	}
}
