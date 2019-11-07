package week2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GreddyAlgorithm {

	public static void main(String[] args) throws IOException {
		// размер суммы
		int [] coin = {10, 5, 2, 1};
		int cost = 29;
		coinCost(coin, cost);
		// задача о расписании
        File file = new File("inputTimeTable.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int tmax = Integer.parseInt(br.readLine()); 
        TimeTable [] tt = new TimeTable[tmax];
        String str="";
        int j=0;
        while((str=br.readLine())!=null) {
        	String [] number =  str.split(" ");
        	tt[j++]=new TimeTable(Integer.parseInt(number[1]), Integer.parseInt(number[0]));
        }
        br.close();
		
		Arrays.sort(tt);
		timetable(tt, tmax);
		// задача о расписании на олимпиаде
        br = new BufferedReader(new FileReader(new File("inputOlimp.txt")));
	    String [] olimp = br.readLine().split(" ");
	    int numTask = Integer.parseInt(olimp[0]);
        tmax = Integer.parseInt(olimp[1]); 
	    olimp = br.readLine().split(" ");
        int [] durationTask = new int [numTask];
        for(int i=0; i<numTask; i++) {
        	durationTask[i] = Integer.parseInt(olimp[i]);
        }
	    br.close();
			
		Arrays.sort(durationTask);
		timeOlimp(durationTask, tmax);
		
		//задача о производителях мороженного
		br = new BufferedReader(new FileReader(new File("inputIceCream.txt")));
		int numIce = Integer.parseInt(br.readLine());
		String [] iceCream = new String [numIce];
        for(int i=0; i<numIce; i++) {
        	iceCream[i] = br.readLine();
        }
        numManufacturerIceCream(iceCream);
		
		// задача о выборе заявок
		BufferedReader brReq = new BufferedReader(new FileReader(new File("inputChoiceRequest.txt")));
		int numRequest = Integer.parseInt(brReq.readLine());
		RequestAudience [] reqAudit = new RequestAudience [numRequest];
		List<RequestAudience> reqList = new ArrayList<>();
		for(int i=0; i< numRequest; i++) {
			String [] req = brReq.readLine().split(" ");
			reqAudit[i] = new RequestAudience(Integer.parseInt(req[0]), Integer.parseInt(req[1]));
			reqList.add(reqAudit[i]);
		}
		brReq.close();
		Arrays.sort(reqAudit);
		choiceRequest(reqAudit);
		
		// задача о выборе заявок с бесконечным числом аудиторий
		Collections.sort(reqList);
		choiceRequestUnLimit(reqList);
		
		// задача о непрерывном рюкзаке
		System.out.println("task backpack");
		br = new BufferedReader(new FileReader(new File("inputBackPack.txt")));
		String [] data = br.readLine().split(" ");
		int numThings = Integer.parseInt(data[0]);
		int maxWeight = Integer.parseInt(data[1]);
			
		Thing [] things = new Thing [numThings];
		for(int i=0; i< numThings; i++) {
			data = br.readLine().split(" ");
			things[i] = new Thing(Integer.parseInt(data[1]), Integer.parseInt(data[0]));
		}
		br.close();
		Arrays.sort(things);
		backpack(things, maxWeight);

	}
	
	private static void numManufacturerIceCream(String[] iceCream) {
		Set<String> currentManufacturer = new HashSet<>();
		int count=1;
		for(int i=0; i<iceCream.length; i++) {
			if(currentManufacturer.contains(iceCream[i])){
				count++;
				currentManufacturer = new HashSet<>();
			}
			currentManufacturer.add(iceCream[i]);
		}
		System.out.println("Manufacturer IceCream "+count);
	}

	private static void timeOlimp(int[] durationTask, int tmax) {
		int count=0;
		long penaltyTime=0;
		int duration=0;
		for(int i=0; i<durationTask.length; i++) {
			if(duration+durationTask[i]<=tmax) {
				count++;
				penaltyTime+=duration+durationTask[i];
				duration+=durationTask[i];
			}else {
				break;
			}
		}
		System.out.println("penalty time " + penaltyTime + " numTask " + count);
	}

	private static void coinCost(int [] coin, int cost) {
		int numCoin=0;
//		Arrays.sort(coin, );
		for(int i=0; i<coin.length; i++) {
			numCoin+=cost/coin[i];
			cost%=coin[i];
		}
		System.out.println("Num coin " +numCoin);
	}
	
	private static void timetable(TimeTable [] costOrderAndDeadline, int tmax) {
		int [] used = new int [tmax];
		long sum =0;
		for(int i=0; i<costOrderAndDeadline.length; i++) {
			int k=costOrderAndDeadline[i].deadLine;
			while(k>=1 && used[k-1]!=0) {
				k--;
			}
			if(k==0)
				continue;
			used[k-1]=costOrderAndDeadline[i].deadLine;
			sum+=costOrderAndDeadline[i].costOrder;
		}
		
		System.out.println("task timetable");
		System.out.println(sum);
	}
	
	private static void choiceRequest(RequestAudience [] request) {
		List<RequestAudience> choice = new ArrayList<>();
		int cnt=1;
		int last = 0;
		choice.add(request[0]);
		for(int i=1; i< request.length; i++) {
			if(request[i].start>=request[last].end) {
				cnt++;
				last=i;
				choice.add(request[last]);
			}
		}
		System.out.println("task choice request");
		System.out.println(cnt);
		System.out.println(choice);
	}
	
	private static void choiceRequestUnLimit(List<RequestAudience> request) {
		List<RequestAudience> choice;
		System.out.println("task choice requestUnLimit");
		System.out.println(request);
		int numAud=0;
		while(!request.isEmpty()) {
			numAud++;
			choice = new ArrayList<>();
			choice.add(request.get(0));
			int last = 0;
				
			for(int i=1; i< request.size(); i++) {
				if(request.get(i).start>=request.get(last).end) {
					last=i;
					choice.add(request.get(last));
				}
			}
			
			System.out.println("№ аудитории " + numAud);
			System.out.println(choice);
			for(RequestAudience ra : choice) {
				request.remove(ra);
			}			
		}
		System.out.println(numAud);
	}
	
	private static void backpack(Thing [] things, int maxWeight) {
		int i=0;
		int w=0;
		int cnt=0;
		while(w+things[i].weight<=maxWeight) {
			w+=things[i].weight;
			cnt+=things[i].cost;
			i++;
		}
		if(i==things.length-1 && maxWeight-w>things[i].weight) {
			cnt+=things[i].cost;			
			w+=things[i].weight;
		}else if(w<maxWeight) {
			cnt+=(maxWeight-w)*things[i].cost/things[i].weight;			
			w+=(maxWeight-w);
		}
		System.out.println(cnt);
	}
}

class TimeTable implements Comparable<TimeTable>{
	int costOrder;
	int deadLine;
	public TimeTable(int costOrder, int deadLine) {
		this.costOrder=costOrder;
		this.deadLine=deadLine;
	}

	@Override
	public int compareTo(TimeTable o) {
		return Integer.compare(this.costOrder,o.costOrder)*(-1);// reverse
	}
}

class RequestAudience implements Comparable<RequestAudience>{
	int start;
	int end;
	public RequestAudience(int start, int end) {
		this.start=start;
		this.end=end;
	}

	@Override
	public int compareTo(RequestAudience o) {
		return Integer.compare(this.end, o.end);
	}

	@Override
	public String toString() {
		return "[" + start + ", " + end + ")";
	}
}

class Thing implements Comparable<Thing>{
	int cost;
	int weight;
	
	public Thing(int cost, int weight) {
		this.cost=cost;
		this.weight=weight;
	}

	@Override
	public int compareTo(Thing o) {
		return Double.compare(this.cost/(double)this.weight, o.cost/(double)o.weight)*(-1);// reverse;
	}
	@Override
	public String toString() {
		return "[" + cost + ", " + weight + "]";
	}
}