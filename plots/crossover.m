clear;
clc;


load crossover

X = [0.0 0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9 1.0]
Y = [0.0 0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9 1.0]


figure;


ph = pcolor(X,Y,crossover)

cd=get(ph,'cdata');
cd(1:2,1:2)=max(cd(:));
set(ph,'cdata',cd)
     
axis([0 1 0 1]);

title('Colormap of QOS vs. mutation and crossover');

axis equal tight 