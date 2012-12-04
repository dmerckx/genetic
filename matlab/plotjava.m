clear;
clc;

load ..\result\resultFbiAdj.txt
load ..\result\resultUiAdj.txt
load ..\result\resultFbiPath.txt
load ..\result\resultUiPath.txt

figure;

subplot(2,2,1);
plotSingleGraph(resultFbiAdj);
title('Adjacency with FBI insertion');
subplot(2,2,2);
plotSingleGraph(resultUiAdj);
title('Adjacency with UI insertion');
subplot(2,2,3);
plotSingleGraph(resultFbiPath);
title('Path with FBI insertion');
axis([0 100 1300 400]);
subplot(2,2,4);
plotSingleGraph(resultUiPath);
title('Path with UI insertion');
