package werewolf.distributerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import werewolf.cardDistributer.father.CardDistributer;
import werewolf.util.classUtil.SubClassesScanner;

/**
 * 发牌器工厂
 * @author zhouzhengyu
 *
 */
public class DistributerFactory {

	private static Map<String,CardDistributer> distributerMap = new HashMap<String,CardDistributer>();
	
	/**
	 * 分发卡牌
	 * @param theme 板子名称
	 * @return 打乱后的牌堆（玩家依次从牌堆最上方取牌）
	 */
	public static String[] distribute(String theme)
	{
		registerAllDistributers();
		return distributerMap.get(theme).distribute();
	}
	
	
	private static void registerAllDistributers()
	{
		List<String> distributers = SubClassesScanner.getDistributersNameList();
		for (String name : distributers)
		{
			try {
				Class<?> distributerClass = Class.forName(name);
				CardDistributer distributer = (CardDistributer) distributerClass.newInstance();
				register(distributer.getTheme(), distributer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 注册发牌器
	 * @param theme 游戏主题（板子）
	 * @param distributer 发牌器
	 */
	public static void register(String theme, CardDistributer distributer)
	{
		if (theme == null || theme.length() == 0 || distributer == null)
		{
			throw new IllegalArgumentException("板子名称或发牌器未初始化，发牌器注册失败");
		}
		distributerMap.putIfAbsent(theme, distributer);
	}
}
