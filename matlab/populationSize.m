clc;
clear;

load ..\result\result-adj-pop.txt
load ..\result\result-path-pop.txt

result_adj_pop

popSizes_adj = result_adj_pop(:,1);

for i = 1:1:size(popSizes_adj,1)
qos_adj(i) = 564/result_adj_pop(i,2);
end

plot(popSizes_adj,qos_adj,'g');
hold on;

popSizes_path = result_path_pop(:,1);

for i = 1:1:size(popSizes_path,1)
qos_path(i) = 564/result_path_pop(i,2);
end

plot(popSizes_path,qos_path,'r');