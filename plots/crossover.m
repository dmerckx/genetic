clear;
clc;


load crossoverAdj
load crossoverPath


X = [0.0 0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9 1.0]
Y = [0.0 0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9 1.0]


figure;

subplot(1,2,1);
pcolor(X,Y,crossoverAdj);
axis([0 1 0 1]);


subplot(1,2,2);
pcolor(X,Y,crossoverPath);
axis([0 1 0 1]);



% get the matrix containing that colormap:
cmap = colormap('jet');
% flip the matrix:
cmap = flipud(cmap);
% apply the new inverted colormap:
colormap(cmap);

title('Colormap of QOS vs. mutation and crossover');

