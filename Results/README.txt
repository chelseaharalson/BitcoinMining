Chelsea Metcalf
4328-8397

------------------ USAGE ------------------
I compiled the program into a jar using the assembly plugin.

First, run the boss like:
time java -jar project1.jar 4

4 is the number of leading zeros for the coins.

Next, run the worker like:
time java -jar project1.jar 128.227.205.151

128.227.205.151 is the IP address of the boss.

As specified in the directions to include build.sbt, one can also run the program like:
sbt "project project1" "run 4"
sbt "project project1" "run 128.227.205.151"


------------------ SIZE OF WORK UNIT ------------------
I conducted my testing on one machine with 8 cores.

The size of work unit that I determined was best is 1000000 because performance (using the time command) was measured to be 764.2% and the time it took to run the program was under a minute. The ratio of CPU time to real time was 7.642, which is good because it is close to 8, which is the amount of cores that the machine had.

Below is a sample of the output from running: time java -jar project1.jar 4 :
chelseametcalf10b8f0	00002c623269872ffaf6682689f898c93016283590156b93cf9adda7ed40b3fe
chelseametcalf117929	00006e9b5293e954db5cb6c62b42adea8a092f8b4ff5dfcb800aa77fe27054e9
chelseametcalf12d3f1	0000cd8a745ec1331e518d52ef10482252fc6662a461c3bc3bbaa4fe92d62dd5
chelseametcalf157b8b	0000f9d8e0f01721029b4d2edbad4f5b13bde559cda1acd77bdd451285b4d59d
chelseametcalf158ae6	0000ded456d53c404287fa689593da7f5acaca97bc712cdbac11c308f669b104
chelseametcalf16029f	00005e9013c3256f18a031cb298ed2b35d6ebbce0750c1d4aa7895cd4ac840b4
chelseametcalf163bae	0000989db02b4cb3ca85d471ae907313bfb16d293751a9460b4e35ecdeebb0e8
chelseametcalf1903b4	0000535b9ba532620dbeec6116cea81ade4ca86f044baf47463aac4bfb47fb2a
chelseametcalf1909a6	0000ce76d9ddad80689a1bf6210517738c9d03cd3fbd51d6644a357feeb68031
chelseametcalf1afa2f	0000872d17497d55a3770cae7bd06bcc14dec6ead559dfda9370350d7a79ee74
chelseametcalf1b5586	00006ec7832eb272d28a456a0dce6508b4663529234f371abec0951773adabc0
chelseametcalf1c0d0b	000022c227a442b77e61f7eeea3ab46805e03de53e0fac6721d629fea121cbaf
chelseametcalf1c3569	0000ebdd4a412aed667b39d0b82fcd4e0fc0b5f83b7f0aed89007e39114d0767
chelseametcalf1dbea3	00000fb3f0c94074caf1b970653a74c04bb6ef46fa6353414fe771eb406d2600
chelseametcalf1dd969	0000493cbb6c3a029ed8d7ddacda48e637d6223c99dfd50f33fe061686ae1327

TOTAL COIN COUNT: 136

383.095u 0.477s 0:50.19 764.2%	0+0k 0+368io 0pf+0w

I tried other values for the work size and measured the performance. I found that increasing the work size actually helped performance. But I decided 1000000 because it took a reasonable amount of time to execute the program.


------------------ RESULT OF 4 LEADING ZEROS ------------------
Please see Coins_1000000_BOSS_4.txt for the exact output and coins found.

Performance of "time java -jar project1.jar 4" :
398.200u 0.485s 0:51.83 769.2%	0+0k 0+312io 0pf+0w

As you can see, the ratio of CPU time to real time is 7.692, which is close to 8.


------------------ RESULT OF 5 LEADING ZEROS ------------------
Please see Coins_1000000_BOSS_5.txt for the exact output and coins found.

Performance of "time java -jar project1.jar 5" :
387.057u 0.542s 0:50.52 767.2%	0+0k 0+432io 0pf+0w

As you can see, the ratio of CPU time to real time is 7.672, which is close to 8.


------------------ COIN WITH THE MOST ZEROS FOUND ------------------
The coin with the most 0's I managed to find was: 
This was my result with a work size of 


------------------ LARGEST NUMBER OF WORKING MACHINES ------------------
When I tested my project, I ssh'd into sand.cise.ufl.edu. From there, I ssh'd into all 8 machines in the 312 room. For example:
ssh cmetcalf@sand.cise.ufl.edu
ssh lin312-02

The max I could ssh into was from machines 02 to 09. All of these machines have 8 cores. I ran my program on all 8 to mine the coins.

In these files, you will find the results of running "time java -jar project1.jar 4" and "time java -jar project1.jar 5" on 8 machines with a work size unit of 1000000.

Coins_1000000_4.txt
Coins_1000000_5.txt

Most coins found with 4 leading zeros: 1276
Most coins found with 5 leading zeros: 68