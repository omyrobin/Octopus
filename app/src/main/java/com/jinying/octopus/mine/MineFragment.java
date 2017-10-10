package com.jinying.octopus.mine;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.jinying.octopus.App;
import com.jinying.octopus.BaseFragment;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.bookmall.webactivity.WebActivity;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.event.EventCutId;
import com.jinying.octopus.event.EventNetConnection;
import com.jinying.octopus.mine.opinion.OpinionFragment;
import com.jinying.octopus.user.UserActivity;
import com.jinying.octopus.util.FragmentUtil;
import com.jinying.octopus.util.ToastUtil;
import com.jinying.octopus.widget.rowview.ContainerView;
import com.jinying.octopus.widget.rowview.GroupDescript;
import com.jinying.octopus.widget.rowview.OnRowClickListener;
import com.jinying.octopus.widget.rowview.RowActionEnum;
import com.jinying.octopus.widget.rowview.RowDescript;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by omyrobin on 2017/8/21.
 */

public class MineFragment extends BaseFragment implements OnRowClickListener {

    @BindView(R.id.custom_containerview)
    ContainerView mContainerView;

    @BindView(R.id.tv_mine_username)
    TextView mUserNameTv;

    @BindView(R.id.tv_mine_login_state)
    TextView mLoginStateTv;

    @Override
    protected int provideContentViewId() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_mine;
    }

    @Override
    protected void initializeView() {
        initContainerView();
        initUser();
    }

    private void initContainerView(){
        ArrayList<GroupDescript> groupDescripts = new ArrayList<>();

        ArrayList<RowDescript> descript = new ArrayList<>();
//        descript.add(new RowDescript(R.drawable.ic_mine_reading_log, RowActionEnum.MINE_READER_LOG.getLable(),null,RowActionEnum.MINE_READER_LOG));
        descript.add(new RowDescript(R.drawable.ic_mine_clear_cache, RowActionEnum.MINE_CLEAR.getLable(),null,RowActionEnum.MINE_CLEAR));
        descript.add(new RowDescript(R.drawable.ic_mine_grade, RowActionEnum.MINE_GRADE.getLable(),null,RowActionEnum.MINE_GRADE));
        descript.add(new RowDescript(R.drawable.ic_mine_opinion, RowActionEnum.MINE_OPINION.getLable(),null,RowActionEnum.MINE_OPINION));
        descript.add(new RowDescript(R.drawable.ic_mine_about, RowActionEnum.MINE_ABOUT.getLable(),null,RowActionEnum.MINE_ABOUT));
        descript.add(new RowDescript(R.drawable.ic_mine_switch_account, RowActionEnum.MINE_SWITCH.getLable(),null,RowActionEnum.MINE_SWITCH));
        groupDescripts.add(new GroupDescript(descript));

        mContainerView.initData(groupDescripts, this);
        mContainerView.notifyDataChange();
    }

    private void initUser(){
        UserBean userBean = App.getInstance().getUserBean();
        if(userBean != null){
            mUserNameTv.setText(userBean.getNickName());
            if(userBean.getIsBinding() == 1){
                mLoginStateTv.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onRowClick(RowActionEnum action) {
        switch (action){
//            case MINE_READER_LOG:
//                ToastUtil.showLong(getActivity(), "功能开发中...请期待后续版本");
//                break;

            case MINE_CLEAR:
                //TODO 前方高能!!!
                ToastUtil.showLong(getActivity(), "清理成功");
                break;

            case MINE_GRADE:
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    ToastUtil.showLong(getActivity(), "未检测到任何应用市场，请安装后再来尝试！");
                }
                break;

            case MINE_OPINION:
                initOpinionFragment();
                break;

            case MINE_ABOUT:
                WebActivity.newInstance(getActivity(), Url.ABOUT_URL, -1 ,"关于");
                break;

            case MINE_SWITCH:
                UserActivity.newInstance(getActivity());
                break;

            default:
                break;
        }
    }

    @OnClick(R.id.tv_mine_login_state)
    public void onClick(View v){
        UserActivity.newInstance(getActivity());
    }

    /**
     * 观察----用户状态改变
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(EventCutId event) {
        initUser();
    }

    /**
     *异常use状态 网络连接后
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(EventNetConnection event) {
        initUser();
    }

    private void initOpinionFragment(){
        OpinionFragment opinionFragment = new OpinionFragment();
        FragmentUtil.hideFragment(getActivity().getSupportFragmentManager())
                .add(android.R.id.content, new OpinionFragment())
                .addToBackStack(null)
                .show(opinionFragment)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
