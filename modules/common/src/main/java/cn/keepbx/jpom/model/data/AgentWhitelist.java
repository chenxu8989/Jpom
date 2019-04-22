package cn.keepbx.jpom.model.data;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.jiangzeyin.common.DefaultSystemLog;
import cn.keepbx.jpom.common.BaseJpomController;
import cn.keepbx.jpom.model.BaseJsonModel;
import cn.keepbx.jpom.system.ExtConfigBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 白名单
 *
 * @author jiangzeyin
 * @date 2019/4/16
 */
public class AgentWhitelist extends BaseJsonModel {
    private List<String> project;
    private List<String> certificate;
    private List<String> nginx;

    public List<String> getProject() {
        return project;
    }

    public void setProject(List<String> project) {
        this.project = project;
    }

    public List<String> getCertificate() {
        return certificate;
    }

    public void setCertificate(List<String> certificate) {
        this.certificate = certificate;
    }

    public List<String> getNginx() {
        return nginx;
    }

    public void setNginx(List<String> nginx) {
        this.nginx = nginx;
    }


    public static List<String> covertToArray(List<String> list) {
        List<String> array = new ArrayList<>();
        for (String s : list) {
            String val = String.format("/%s/", s);
            val = BaseJpomController.pathSafe(val);
            if (StrUtil.SLASH.equals(val)) {
                continue;
            }
            if (array.contains(val)) {
                continue;
            }
            // 判断是否保护jpom 路径
            if (val == null || val.startsWith(ExtConfigBean.getInstance().getPath())) {
                return null;
            }
            array.add(val);
        }
        return array;
    }

    /**
     * 转换为字符串
     *
     * @param jsonArray jsonArray
     * @return str
     */
    public static String convertToLine(List<String> jsonArray) {
        try {
            return CollUtil.join(jsonArray, "\r\n");
        } catch (Exception e) {
            DefaultSystemLog.ERROR().error(e.getMessage(), e);
        }
        return "";
    }
}
