package com.blog.blog;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

@AutoConfigureMockMvc
@SpringBootTest
class BlogApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("test","20200401愚人节快乐");
        String test = (String) redisTemplate.opsForValue().get("test");
        System.out.println("redis:{}"+test);
    }
    @Test
    public void  repeat(){
        HashMap<String,Integer> repeat = new HashMap<>();
        repeat.put("1",3);
        Integer put = repeat.put("1", 4);
        System.out.println("put进去的值{}"+put);
    }

    @Test
    public void  testLongStr(){
        int key = 10_000_000;
        String str = "werermty";
        int count = lengthOfLongestSubstring(str);
        System.out.println("============"+key);
    }


    public int lengthOfLongestSubstring(String s) {
        int start = 0,resultLength = 0,displament = s.length(),currentLength = 0;
        Map<Character,Integer> repeatMap = new HashMap<>(displament);
        for (int i = 0;i < displament;i++){
            Character currentChar = s.charAt(i);
            Integer returnVal = repeatMap.put(currentChar, i); //(1)
            // 没有重复、重复元素坐标小于当前阅读指针位置继续进行遍历
            if (returnVal == null || returnVal < start){   //(2)
                continue;
            }
            // 当前阅读窗口内重复则变更阅读指针并计算最大的不重复子串长度
            currentLength = i - start;
            resultLength = Math.max(resultLength,currentLength);
            start = returnVal + 1;   //(3)
            // 当遍历到字符串结束长度都不超过当前最大子串长度则直接返回
            if (displament - start <= resultLength){   //(4)
                break;
            }
        }
        // 防止结尾那一次没有重复计算值   //(5)
        currentLength = displament - start;
        resultLength = Math.max(currentLength,resultLength);
        return resultLength;
    }

    @Test
    public void toswr(){
        int[] nums = {2,7,11,15};
        int target = 9;
        int[] ints = twoSum(nums, target);
        System.out.println("==============="+ Arrays.toString(ints));
        String key = "1000";
        int hashcode = key.hashCode();
        System.out.println("==========="+hashcode);
    }


    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> numMap = new HashMap();
        for(int i =0;i<nums.length;i++){
            Integer needNum =   target-nums[i];
            Integer  index=  numMap.get(nums[i]);
            if(Objects.isNull(index)){
                numMap.put(needNum,i);
                continue;
            }
            return new int[]{index,i};
        }
        return null;
    }


    @Test
    public void threeAdd(){
        int[] nums = {-1,0,1,2,-1,-4};
        //List<List<Integer>> lists = threeSum(nums);
        //System.out.println("================"+ JSON.toJSONString(lists));
    }

    public List<List<Integer>> threeSumMy(int[] nums) {
        List<List<Integer>> data  = new ArrayList<>();
        for(int i=0;i<nums.length;i++){
            for(int j=i+1; j<nums.length ;j++){
                Integer current =  nums[i]+nums[j];
                Integer needNum =   0 - current;
                List<Integer>  numsList=  Arrays.stream(nums).boxed().collect(Collectors.toList());
                if(numsList.contains(needNum)){
                    Integer index = numsList.indexOf(needNum);
                    if(index == i || index == j ){
                        continue;
                    }
                    List<Integer> list  = new ArrayList();
                    list.add(nums[i]);
                    list.add(nums[j]);
                    list.add(needNum);
                    if (!data.containsAll(list)){
                        data.add(list);
                    }
                }

            }
        }
        return data;
    }


    @Test
    public void  testStream(){

        String[] ids ={"12313144","1232342356"};
        //Arrays.stream(ids).mapToLong(Long::valueOf).collect()
    }

   /* public List<List<Integer>> threeSum(int[] nums) {
        Integer[] sNums = Arrays.stream( nums ).boxed().toArray( Integer[]::new );
        Arrays.sort(sNums);
        HashSet<List<Integer>> retList = new HashSet<List<Integer>>();
        for(int i=1;i<sNums.length-1;i++){
            List<Integer> integers = Arrays.asList(sNums);
            integers.subList(0, i);
            HashSet<Integer> right = new HashSet<Integer>(Arrays.asList(sNums).subList(i+1,sNums.length));
            for(int j=0;j<left.length;j++){
                Integer leftSum=sNums[i]+sNums[j];
                if(right.contains(0-leftSum)){
                    ArrayList<Integer> r=new ArrayList<Integer>();
                    r.add(sNums[i]);
                    r.add(sNums[j]);
                    r.add(0-leftSum);
                    retList.add(r);
                }else{
                    continue;
                }
            }
        }
        return new ArrayList<List<Integer>>(retList);
    }*/

   @Test
    public void testThrowException(){
      // mockMvc.perform()
       //mockMvc.perform(get("/")).andExpect()
   }

   @Test
    public void testArrayAslist(){
       List<String> strings = Arrays.asList("apple", "banner", "orange");
       System.out.println("===="+strings);
       strings.add("peer");
       System.out.println("======"+strings);
   }
}
