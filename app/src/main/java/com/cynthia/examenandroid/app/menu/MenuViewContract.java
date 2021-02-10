package com.cynthia.examenandroid.app.menu;

import com.cynthia.examenandroid.business.models.ExError;

public interface MenuViewContract {
    void onSuccessFiel();

    void onFailedFiel(ExError error);
}
