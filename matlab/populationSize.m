%clc;
%clear;

%load ..\result\result-adj-pop.txt
%load ..\result\result-path-pop.txt

popSizes_adj = result_adj_pop(:,1);

qos_adj = result_adj_pop(:,2)/564;

plot(popSizes_adj,qos_adj,'g');
hold on;

popSizes_path = result_path_pop(:,1);

qos_path = result_path_pop(:,2)/564;

plot(popSizes_path,qos_path,'r');