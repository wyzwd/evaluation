package com.evaluation.system.Service;

import com.evaluation.system.domain.ShowStu;
import com.evaluation.system.domain.basic;

import java.util.List;

public interface BasicService {

    public List<ShowStu> ShowScore();

    public basic findbynumber(String number);

    public void updatabasic(basic ba);
}
