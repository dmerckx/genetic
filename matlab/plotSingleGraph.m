function [  ] = plotSingleGraph( data )
%PLOTSINGLEGRAPH Summary of this function goes here
%   Detailed explanation goes here

sizeData = size(data, 1);

%figure figureNb;

% best
plot(1:1:sizeData,data(:,1), 'r');

hold on;

% average
plot(1:1:sizeData,data(:,2),'b');

hold on;

% worst
plot(1:1:sizeData,data(:,3),'g');

hold on;

end

