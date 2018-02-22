package info.zhiqing.tinypiratebay.spider;

import java.io.IOException;
import java.util.List;

import info.zhiqing.tinypiratebay.entities.Torrent;
import info.zhiqing.tinypiratebay.entities.TorrentDetail;

/**
 * Created by zhiqing on 18-1-4.
 */

public interface Spider {

    //通过URL获取种子列表
    public List<Torrent> list(String url) throws IOException;

    //通过关键词搜索
    List<Torrent> search(String keyword, int page) throws IOException;

    //通过类型编号浏览列表
    List<Torrent> browse(String typeCode, int page) throws IOException;

    //查看某类型的排行榜
    List<Torrent> top(String typeCode, int page) throws IOException;

    //查看特定用户上传的列表
    List<Torrent> user(String username, int page) throws IOException;

    //查看最新列表
    List<Torrent> recent(String typeCode) throws IOException;

    //通过编号获取详细信息
    TorrentDetail detail(String code) throws IOException;


}
