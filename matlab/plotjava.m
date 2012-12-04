clear;
clc;

load ..\result\resultFbiAdj.txt
load ..\result\resultUiAdj.txt
load ..\result\resultFbiPath.txt
load ..\result\resultUiPath.txt
load ..\result\resultNewPath.txt
load ..\result\resultNewAdj.txt

figure;

subplot(2,3,1);
plotSingleGraph(resultFbiAdj);
title('Adjacency with FBI insertion');
subplot(2,3,2);
plotSingleGraph(resultUiAdj);
title('Adjacency with UI insertion');
subplot(2,3,3);
plotSingleGraph(resultNewAdj);
title('Adjacency with Own insertion');
subplot(2,3,4);
plotSingleGraph(resultFbiPath);
title('Path with FBI insertion');
axis([0 100 1300 4000]);
subplot(2,3,5);
plotSingleGraph(resultUiPath);
title('Path with UI insertion');
subplot(2,3,6);
plotSingleGraph(resultNewPath);
title('Path with Own insertion');