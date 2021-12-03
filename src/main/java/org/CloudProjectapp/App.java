package org.CloudProjectapp;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.AmazonClientException;
import java.util.Scanner;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.Instance;
import java.lang.String;
import com.amazonaws.services.ec2.model.DryRunResult;
import com.amazonaws.services.ec2.model.DryRunSupportedRequest;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;


/**
 * CloudProject
 * 2015041076 jo myeonghyeon
 */
public class App 
{
	static AmazonEC2      ec2;
	private static void init() throws Exception {
		
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		try {
			credentialsProvider.getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException(
					"Cannot load the credentials from the credential profiles file. " +
					"Please make sure that your credentials file is at the correct " +
					"location (~/.aws/credentials), and is in valid format.",
					e);
		}
		ec2 = AmazonEC2ClientBuilder.standard()
			.withCredentials(credentialsProvider)
			.withRegion("us-east-2") 
			.build();
	}
	public static void main(String[] args) throws Exception {
		init();

		Scanner menu = new Scanner(System.in);
		Scanner id_string = new Scanner(System.in);
		int number = 0;
		while(true)
		{
			System.out.println("                                                            ");
			System.out.println("                                                            ");
			System.out.println("------------------------------------------------------------");
			System.out.println("           Amazon AWS Control Panel using SDK               ");
			System.out.println("                                                            ");
			System.out.println("  Cloud Computing, Computer Science Department              ");
			System.out.println("                           at Chungbuk National University  ");
			System.out.println("------------------------------------------------------------");
			System.out.println("  1. list instance                2. available zones         ");
			System.out.println("  3. start instance               4. available regions      ");
			System.out.println("  5. stop instance                6. create instance        ");
			System.out.println("  7. reboot instance              8. list images            ");
			System.out.println("                                 99. quit                   ");
			System.out.println("------------------------------------------------------------");
			System.out.print("Enter an integer: ");
			number=menu.nextInt(); 
				switch(number) {
					case 1:
						System.out.println("case 1");
						listInstances();
						break;
					case 2:
						System.out.println("case 2");
						availableZones();
						break;
					case 3:
						System.out.println("case 3");
						startInstance();
						break;
					case 4:
						System.out.println("case 4");
						availableRegions();
						break;
					case 5:
						System.out.println("case 5");
						stopInstance();
						break;
					case 6:
						System.out.println("case 6");
						createInstance();
						break;
					case 7:
						System.out.println("case 7");
						rebootInstance();
						break;
					case 8:
						System.out.println("case 8");
						listImages();
						break;
					case 99:
						System.out.println("quit");
						System.exit(0);
					default:
						System.out.println("Incorrect input.");
						break;
				}
		}

	}
	public static void listInstances()
	{
		System.out.println("Listing instances....");
		boolean done = false;
		DescribeInstancesRequest request = new DescribeInstancesRequest();
		while(!done) {
			DescribeInstancesResult response = ec2.describeInstances(request);
			for(Reservation reservation : response.getReservations()) {
				for(Instance instance : reservation.getInstances()) {
					System.out.printf(
							"[id] %s, " +
							"[AMI] %s, " +
							"[type] %s, " +
							"[state] %10s, " +
							"[monitoring state] %s",
							instance.getInstanceId(),
							instance.getImageId(),
							instance.getInstanceType(),
							instance.getState().getName(),
							instance.getMonitoring().getState());
				}
				System.out.println();
			}
			request.setNextToken(response.getNextToken());
			if(response.getNextToken() == null) {
				done = true;
			}
		}
	}
	public static void availableZones(){
	System.out.println("Avilable zones....");

	}
	public static void startInstance(){
	Scanner scanner = new Scanner(System.in);
	System.out.println("Start instance....");
	System.out.print("Enter instance id: ");
	String instance;
	instance = scanner.nextLine();
	
	DryRunSupportedRequest<StartInstancesRequest> dry_request =
            () -> {
            StartInstancesRequest request = new StartInstancesRequest()
                .withInstanceIds(instance);

            return request.getDryRunRequest();
        };

	DryRunResult dry_response = ec2.dryRun(dry_request);

        if(!dry_response.isSuccessful()) {
            System.out.printf(
                "Failed dry run to start instance %s", instance);

            throw dry_response.getDryRunResponse();
        }

        StartInstancesRequest request = new StartInstancesRequest()
            .withInstanceIds(instance);

        ec2.startInstances(request);

        System.out.printf("Successfully started instance %s", instance);

	}
	public static void availableRegions(){
	System.out.println("Available regions....");
	}
	public static void stopInstance(){
	System.out.println("Stop instance....");

	}
	public static void createInstance(){
	System.out.println("Create instance....");
	}
	public static void rebootInstance(){
	System.out.println("Reboot instance....");
	}
	public static void listImages(){
	System.out.println("List images....");
	}
}
