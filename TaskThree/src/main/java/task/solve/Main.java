package task.solve;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Initializations
        Random random = new Random();
        int urlNum = 5;//random.nextInt(5);
        int threadNum = 50;
        int clickNum = 10;

        Map<String, Integer> tempMap = new HashMap<>(urlNum);
        for (int i = 0; i < urlNum; i++) {
            tempMap.put(String.valueOf(i), 0);
        }
//        List<String> urlList = new ArrayList<>(urlNum);
//        urlList.addAll(tempMap.keySet());
        urlStats urlStat = new urlStats(tempMap);
        List<String> urlList = urlStat.getList();

//        UserActivity
        ExecutorService users = Executors.newFixedThreadPool(threadNum);
//        for (int i = 0; i < threadNum; i++) {
            for (int i = 0; i < clickNum * threadNum; i++) {
                String urlCurr = urlList.get(random.nextInt(urlNum));
                Future<String> act = users.submit(new userActivity(urlCurr));
                urlStat.updateStat(act.get());
            }
//        }

        users.shutdown();

//        Result
        urlStat.urlStatView();
    }
}

class userActivity implements Callable<String> {

    private String url;
    public userActivity(String url) {
        this.url = url;
    }

    @Override
    public String call() throws Exception {
//        wait(100);
        System.out.println(this.url);
        return url;
    }
}

class urlStats {
    private Map<String, Integer> urlStat;

    public urlStats(Map<String, Integer> urlStat) {
        this.urlStat = urlStat;
    }

    public synchronized void updateStat(String url) {
        this.urlStat.put(url, this.urlStat.get(url) + 1);
    }

    public List<String> getList() {
        List<String> urlList = new ArrayList<>(this.urlStat.size());
        urlList.addAll(this.urlStat.keySet());
        return urlList;
    }
    public void urlStatView() {
        int sum = 0;
        for (String key: this.urlStat.keySet()) {
            sum += this.urlStat.get(key);
        }
        System.out.println("Full clicks = " + sum);
        System.out.println(this.urlStat);
    }
}
