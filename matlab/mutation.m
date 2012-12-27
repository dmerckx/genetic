clear;
clc;

load ..\result\result-adj-mutation-exchange.txt
load ..\result\result-adj-mutation-insertion.txt
load ..\result\result-adj-mutation-inversion.txt
load ..\result\result-adj-mutation-simpleInversion.txt

load ..\result\result-path-mutation-exchange.txt
load ..\result\result-path-mutation-insertion.txt
load ..\result\result-path-mutation-inversion.txt
load ..\result\result-path-mutation-simpleInversion.txt

X = 1:1:size(result_adj_mutation_exchange(:,2),1);

qos_adj_exc = result_adj_mutation_exchange(:,1);
qos_adj_ins = result_adj_mutation_insertion(:,1);
qos_adj_inv = result_adj_mutation_inversion(:,1);
qos_adj_sim = result_adj_mutation_simpleInversion(:,1);

qos_path_exc = result_path_mutation_exchange(:,1);
qos_path_ins = result_path_mutation_insertion(:,1);
qos_path_inv = result_path_mutation_inversion(:,1);
qos_path_sim = result_path_mutation_simpleInversion(:,1);

plot(X, qos_adj_exc, 'g');
hold on;
plot(X, qos_adj_ins, 'r');
hold on;
plot(X, qos_adj_inv, 'b');
hold on;
plot(X, qos_adj_sim, 'c');
hold on;
plot(X, qos_path_exc, 'g');
hold on;
plot(X, qos_path_ins, 'r');
hold on;
plot(X, qos_path_inv, 'b');
hold on;
plot(X, qos_path_sim, 'c');
hold off;