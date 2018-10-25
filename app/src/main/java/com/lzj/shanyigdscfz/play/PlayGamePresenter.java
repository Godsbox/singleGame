/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyigdscfz.play;

import com.lzj.arch.app.PassivePresenter;
import com.lzj.arch.core.Contract;
import com.lzj.arch.file.KeyValueCache;
import com.lzj.arch.file.KeyValueCaches;

import static com.lzj.shanyigdscfz.AppConstant.PREFS_APP;
import static com.lzj.shanyigdscfz.AppConstant.PREF_GAME_PLAY_GUIDE;


/**
 * 玩游戏表现者。
 *
 * @author 吴吉林
 */
public class PlayGamePresenter
        extends PassivePresenter<PlayGameContract.PassiveView, PlayGameModel, Contract.Router>
        implements PlayGameContract.Presenter {

    @Override
    protected void onNewViewAttach(boolean reattach, boolean isVisibleToUser) {
        super.onNewViewAttach(reattach, isVisibleToUser);
        KeyValueCache cache = KeyValueCaches.get(PREFS_APP);
        boolean guide = cache.getBoolean(PREF_GAME_PLAY_GUIDE);
        getModel().setQuitGuideShown(!guide);
        if (!guide) {
            getView().toggleQuitGuide(true);
            cache.putBoolean(PREF_GAME_PLAY_GUIDE, true).apply();
        }
    }

    @Override
    public void onBackPressed() {
        if (getModel().isQuitGuideShown()) {
            getView().toggleQuitGuide(false);
            getModel().setQuitGuideShown(false);
            return;
        }
        boolean shown = getModel().isQuitConfirmShown();
        getModel().setQuitConfirmShown(!shown);
        getView().toggleQuitConfirm(!shown);
    }

}