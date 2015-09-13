Chelsea Metcalf

------------------ USAGE & SOURCE CODE ------------------
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

In the code, the variables workSize and cycles in Boss.scala are meant to be changed. The cycles variable represents how many actors are spawned. I changed these two things when testing.

----------------------------------------------------------------------------------------


------------------ SIZE OF WORK UNIT ------------------
I conducted my testing on one machine with 8 cores.

The size of work unit that I determined was best is 1,000,000 because performance (using the time command) was measured to be 770.9% and the time it took to run the program was under a minute. The ratio of CPU time to real time was 7.709, which is good because it is close to 8, which is the amount of cores that the machine had. I spawned 8 actors.

Below is a sample of the output from running: "time java -jar project1.jar 4" (the full version is in Coins_4_Boss.txt):
chelseametcalf308f53	000087c3bfea6fbbdcb211a92a2d0d585e0050a790195c08d11ce2da886d4e38
chelseametcalf312e74	0000e8f86ddff65b08aae6fd6b4b1bbad1cb9630175a007d1b6456e7f181a4ac
chelseametcalf315f3a	0000d74f85f2f8d97702a2f469619fc89dd30f294ab3ef9174a462683dec3e00
chelseametcalf32dceb	000003923b5a4a3dd6744365abda95244a1a952bf2f4fa25a331c01ff061e28b
chelseametcalf339426	000022de69faf796988c5a0fdb9f929337151e906bfb3f0a32ef4e3469bf59a5
chelseametcalf33c61d	000061c855543cd36497d6432e09429d4b5fd5fa32ab56e35b9d126fb4dfcd49
chelseametcalf3487ef	0000b271248fe7d100382582953340226a1b70e493655d3c4ce40a89f79612d8
chelseametcalf36599a	00001ba91ad7b8d13e815e9e3b2b8042a358835419158c5cf17c2e88a8c7fbc4
chelseametcalf367a9e	0000ec1cd936913e1e6aa5236f0f091e6bae5aa27d50ccbf537140b87aa7221f
chelseametcalf398684	00009155367e449c1162b2597409bbdd1d1e2a6db91fec3e8df7711d8dbbcd61
chelseametcalf39c750	00008ada4f1dda49fedfe0d68f2b97da47ce0cc03d72f8c88b9dfaaa8d7e07f3
chelseametcalf39e4b6	00002980751565efe56a7a24fcceaa8218dac5939d68923d237ca75f528171dd
chelseametcalf3a02f5	000029f7db8c30068e610378b2cac710c13ddeb62c9ff653bb13ee9136c6e889
chelseametcalf3a9b9b	000055717688843cea008fa076bcc0d2395a39e8becc6ac99e67c5c62f48a524
chelseametcalf3bd58b	0000254ed96fb3d79670c1437e480b2566fed12b4c09992a42c0fe034aadee56

TOTAL COIN COUNT: 113

313.901u 0.505s 0:40.78 770.9%	0+0k 0+248io 0pf+0w

I tried other values for the work size and measured the performance. I found that increasing the work size actually helped performance. But I decided to use 1,000,000 because it took a reasonable amount of time to execute the program.

----------------------------------------------------------------------------------------


------------------ RESULT OF 4 LEADING ZEROS ------------------
Please see Coins_4_Boss.txt for the exact output and coins found.

Work size unit: 1,000,000
Cycles/Actors: 8

Total coins found: 113

Performance of "time java -jar project1.jar 4" :
313.901u 0.505s 0:40.78 770.9%	0+0k 0+248io 0pf+0w

As you can see, the ratio of CPU time to real time is 7.709, which is close to 8.

----------------------------------------------------------------------------------------


------------------ RESULT OF 5 LEADING ZEROS ------------------
Please see Coins_5_Boss.txt for the exact output and coins found.

Work size unit: 1,000,000
Cycles/Actors: 8

Total coins found: 9

Performance of "time java -jar project1.jar 5" :
318.344u 0.461s 0:41.38 770.4%	0+0k 0+232io 0pf+0w

As you can see, the ratio of CPU time to real time is 7.704, which is close to 8.

----------------------------------------------------------------------------------------


------------------ COIN WITH THE MOST ZEROS FOUND ------------------
The coin with the most 0's I managed to find was:
chelseametcalf5d416e4	00000001f6e834296093ce424f45749f5eac5d51da623886b7ff22f3c81b06d7
(7 zeros)

This was my result with a work size of 10,000,000 and spawning 16 actors on one machine.
6033.969u 4.173s 12:54.17 779.9%	0+0k 0+1224io 0pf+0w

----------------------------------------------------------------------------------------


------------------ LARGEST NUMBER OF WORKING MACHINES ------------------
When I tested my project, I ssh'd into sand.cise.ufl.edu. From there, I ssh'd into all 8 machines in the 312 room. For example:
ssh cmetcalf@sand.cise.ufl.edu
ssh lin312-02

The max I could ssh into was from machines 02 to 09. All of these machines have 8 cores. I ran my program on all 8 to mine the coins.

In these files, you will find the results of running "time java -jar project1.jar 4" and "time java -jar project1.jar 5" on 8 machines with a work size unit of 1,000,000.

Work size unit: 1,000,000
Cycles/Actors: 8

Coins_4_MoreWorkers.txt
Coins_5_MoreWorkers.txt

Most coins found with 4 leading zeros: 1010
311.742u 0.750s 0:55.71 560.9%	0+0k 34504+568io 1pf+0w

Most coins found with 5 leading zeros: 59
304.598u 0.757s 0:56.60 539.4%	0+0k 0+312io 0pf+0w

----------------------------------------------------------------------------------------


------------------ MORE FILES (RESULTS) ------------------
Coins_6_Boss.txt - 1 machine; work size: 10,000,000; 16 cycles/actors
Coins_7_Boss.txt - 1 machine; work size: 10,000,000; 16 cycles/actors
Coins_7_MoreWorkers.txt - 8 machines; work size: 10,000,000; 16 cycles/actors

----------------------------------------------------------------------------------------