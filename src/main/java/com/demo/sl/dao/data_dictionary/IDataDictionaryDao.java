package com.demo.sl.dao.data_dictionary;

import com.demo.sl.entity.DataDictionary;

import java.util.List;

/**
 * Created by pei on 2017/3/6.
 */
public interface IDataDictionaryDao {
    /**
     * 根据条件的到数据字典表的数据
     * @param dataDictionary 查询条件
     * @return
     */
    List<DataDictionary> getDataDictionaryList(DataDictionary dataDictionary);
}
