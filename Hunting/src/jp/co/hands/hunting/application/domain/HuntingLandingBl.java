package jp.co.hands.hunting.application.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.helper.YamlHelper;
import jp.co.hands.hunting.repository.impl.HuntingModelRepository;
import jp.co.hands.hunting.utils.HuntingIndexInfo;
import jp.co.hands.hunting.utils.filename.YamlUtils;

@Stateless
public class HuntingLandingBl {
	
	@Inject
	private HuntingModelRepository huntingModelRepository;
	
	public List<HuntingModel> getSelectedModel() {
		YamlHelper<HuntingIndexInfo> yamlHelper = new YamlHelper();
		List<HuntingModel> huntingModelList = new ArrayList<>();
		String[] modelIds;
		try {
			modelIds = yamlHelper.getYamlInfo(HuntingIndexInfo.class, YamlUtils.HUNTING_INDEX_FILE_NAME).getModelIds();
			huntingModelList = huntingModelRepository.findByKeyList(modelIds);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return huntingModelList;
	}

}
