package jp.co.hands.hunting.manage.domain.model;

import java.io.IOException;
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
public class AdminModelUpdateBl {

	@Inject
	private HuntingModelRepository huntingModelRepository;

	/**
	 * モデルデータの更新
	 * 
	 */
	public void updateModel(HuntingModel targetModel, Part uploadedFile) {

		FacesContext fc = FacesContext.getCurrentInstance();

		// 更新できる状態になっているか確認（targetModelが問題なく保持されているか）
		if (!Optional.ofNullable(targetModel.getUserId()).isPresent()) {
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "もう一度最初からやり直してください。"));
			return;
		}

		String userId = targetModel.getUserId();

		// DB内にデータがそもそも存在している?
		if (!Optional.ofNullable(huntingModelRepository.findByKey(userId)).isPresent()) {
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "すでにデータが削除されています。"));
		}

		// 
		if (Optional.ofNullable(targetModel.getProfilePictureUrl()).isPresent()
				&& targetModel.getProfilePictureUrl().startsWith("http")) {
			YamlHelper<HuntingUserImg> yh = new YamlHelper();
			try {
				String path = yh.getYamlInfo(HuntingUserImg.class, YamlUtils.HUNTING_MODEL_IMG_PATH).getImgPath();
				String url = targetModel.getProfilePictureUrl();
				byte[] imgByte = FileHundler.imageConvFromWeb(url);
				String imgName = System.currentTimeMillis() + "_" + targetModel.getUserId()
						+ url.substring(url.lastIndexOf("."));
				FileHundler.writeByteToImageFile(path + imgName, imgByte);
				targetModel.setProfilePictureUrl(imgName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (Optional.ofNullable(uploadedFile).isPresent()) {
			YamlHelper<HuntingUserImg> yh = new YamlHelper();
			try {
				String path = yh.getYamlInfo(HuntingUserImg.class, YamlUtils.HUNTING_MODEL_IMG_PATH).getImgPath();
				String name = uploadedFile.getSubmittedFileName();
				byte[] imgByte = FileHundler.fileConvToByte(uploadedFile);
				String imgName = System.currentTimeMillis()+"_"+targetModel.getUserId()+name.substring(name.lastIndexOf("."));
				FileHundler.writeByteToImageFile(path + imgName, imgByte);
				targetModel.setProfilePictureUrl(imgName);			
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		// データの更新
		huntingModelRepository.updata(targetModel, userId);
		fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "更新が完了しました。"));

	}

}
