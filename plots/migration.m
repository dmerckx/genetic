clear;
clc;


load migrationAdjcirc
load migrationAdjrand
load migrationPathcirc
load migrationPathrand

migrationAdjcirc = 564 ./ transpose(migrationAdjcirc);
migrationAdjrand = 564 ./ transpose(migrationAdjrand);
migrationPathcirc = 564 ./ transpose(migrationPathcirc);
migrationPathrand = 564 ./ transpose(migrationPathrand);

X = 0:0.05:1;

figure;

subplot(2,2,1);
plot(X, migrationAdjcirc)

legend('4 islands','8 islands', '12 islands')
title('Adjacency with circular migration: QOS vs immigration perc');

subplot(2,2,2);
plot(X, migrationAdjrand)

legend('4 islands','8 islands', '12 islands')
title('Adjacency with random migration: QOS vs immigration perc');


subplot(2,2,3);
plot(X, migrationPathcirc)

legend('4 islands','8 islands', '12 islands')
title('Path with circular migration: QOS vs immigration perc');


subplot(2,2,4);
plot(X, migrationPathrand)

legend('4 islands','8 islands', '12 islands')
title('Path with random migration: QOS vs immigration perc');

% get the matrix containing that colormap:
%cmap = colormap('jet');
% flip the matrix:
%cmap = flipud(cmap);
% apply the new inverted colormap:
%colormap(cmap);