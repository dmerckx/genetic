clear;
clc;


load crossoverAdj
load crossoverPath

X = 0:0.05:1
Y = 0:0.05:1

figure;

%Values for the colormap
cmin = 800;
cmax = 2600;

subplot(1,2,1);
pcolor(X,Y,crossoverAdj);
axis([0 1 0 1]);
title('Colormap of QOS vs. mutation and crossover');
xlabel('Crossover (percentage)');
ylabel('Mutation (percentage)');

set(gca,'clim',[cmin cmax]);

subplot(1,2,2);
pcolor(X,Y,crossoverPath);
axis([0 1 0 1]);
set(gca,'clim',[cmin cmax]);
% get the matrix containing that colormap:
cmap = colormap('jet');
% flip the matrix:
cmap = flipud(cmap);
% apply the new inverted colormap:
colormap(cmap);

title('Colormap of QOS vs. mutation and crossover');
xlabel('Crossover (percentage)');
ylabel('Mutation (percentage)');