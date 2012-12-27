clear;
clc;

load ..\result\result-adj-rws.txt
load ..\result\result-adj-sus.txt
load ..\result\result-adj-tournament.txt

figure;

subplot(1,3,1);
plotSingleGraph(result_adj_rws);
title('Adjacency with RWS selection');
subplot(1,3,2);
plotSingleGraph(result_adj_sus);
title('Adjacency with SUS selection');
subplot(1,3,3);
plotSingleGraph(result_adj_tournament);
title('Adjacency with tournament selection');
