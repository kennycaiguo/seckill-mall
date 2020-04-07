package cc.vant.seckillmall.service;

import cc.vant.seckillmall.mapper.FavoritesMapper;
import cc.vant.seckillmall.mapper.UserMapper;
import cc.vant.seckillmall.model.Favorites;
import cc.vant.seckillmall.model.User;
import cc.vant.seckillmall.pojo.user.req.*;
import cc.vant.seckillmall.pojo.user.rsp.ViewFavoritesRsp;
import cc.vant.seckillmall.util.Response;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FavoritesMapper favoritesMapper;

    public boolean isUserLogin(String userName, String password) {
        User entity = new User();
        entity.setUserName(userName);
        User user = userMapper.selectOne(Wrappers.query(entity));
        if (password.equals(user.getPassword()) && user.getStatus()) {
            return true;
        }
        return false;
    }

    public void userSignIn(UserSignInReq req) {
        User entity = new User();
        entity.setUserName(req.getUserName());
        entity.setPassword(req.getPassword());
        entity.setSex(req.getSex());
        entity.setTel(req.getTel());
        userMapper.insert(entity);
    }

    /**
     * @return true 如果名字已存在
     */
    public boolean isNameExist(String name) {
        User entity = new User();
        entity.setUserName(name);
        User user = userMapper.selectOne(Wrappers.query(entity));
        return user != null;
    }

    public void addToFavorites(AddToFavoritesReq req) {
        Favorites entity = req.toFavorites();
        favoritesMapper.insert(entity);
    }

    /**
     * 检查商品是否已经加入到收藏夹了
     */
    public boolean isFavoritesGoodsExist(Integer goodsId) {
        Favorites entity = new Favorites();
        entity.setGoodsId(goodsId);
        Favorites rst = favoritesMapper.selectOne(Wrappers.query(entity));
        return rst != null;
    }

    public ViewFavoritesRsp viewFavorites(ViewFavoritesReq req) {
        Favorites entity = new Favorites();
        entity.setUserId(req.getUserId());
        entity.setStatus(Boolean.TRUE);
        List<Favorites> favorites = favoritesMapper.selectList(Wrappers.query(entity));
        ViewFavoritesRsp rsp = new ViewFavoritesRsp();
        rsp.setFavoritesList(favorites);
        return rsp;
    }

    public boolean isFavoritesIdExist(Integer favId) {
        Favorites favorites = favoritesMapper.selectById(favId);
        return favorites != null;
    }

    public void deleteFavorites(DeleteFavoritesReq req) {
        favoritesMapper.deleteById(req.getFavId());
    }

    public Response<?> userChangePassword(UserChangePasswordReq req) {
        User user = userMapper.selectById(req.getUserId());
        if (user == null) {
            return Response.fail("userId不合法");
        }
        if (user.getPassword().equals(req.getOldPassword())) {
            User entity = new User();
            entity.setUserId(req.getUserId());
            entity.setPassword(req.getNewPassword());
            userMapper.updateById(entity);
            return Response.success();
        }
        return Response.fail("密码错误");
    }
}