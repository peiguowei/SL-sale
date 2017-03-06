package com.demo.sl.service.data_dictionary;

import com.demo.sl.dao.data_dictionary.IDataDictionaryDao;
import com.demo.sl.entity.DataDictionary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典业务
 */
@Service
public class DataDictionaryService {
    @Resource
    private IDataDictionaryDao dataDictionaryDao;

    /**
     * 查询数据字典表业务 得到数据字典表信息
     * @param dataDictionary 查询的条件
     * @return
     */
    public List<DataDictionary> getDataDictionaryListService(DataDictionary dataDictionary){
        return dataDictionaryDao.getDataDictionaryList(dataDictionary);
    }
}
