clear;
clc;


load islandsRef
load islandsCirc
load islandsRand

from = 44;
to = 56;

islandsRef = 564 ./ transpose(islandsRef);
islandsCirc = 564 ./ transpose(islandsCirc);
islandsRand = 564 ./ transpose(islandsRand);


best(1, :) = islandsRef(1,:);
best(2, :) = islandsCirc(1,:);
best(3, :) = islandsRand(1,:);

Xbest = 1:450;

afg(1, :) = diff(islandsRef(1,:));
afg(2, :) = diff(islandsCirc(1,:));
afg(3, :) = diff(islandsRand(1,:));

Xafg = 1:449;


X1 = 40:60;
X2 = 190:210;

figure;

subplot(5,2,1:2);

plot(Xbest, best);
legend('Reference','Circular', 'Random')
title('QOS(generation)')

subplot(5,2,3:4);

plot(Xafg, afg);
title('derivate of QOS(generation)')

subplot(5,2,5);

plot(X1, islandsRef(:, X1));
legend('best island','average islands', 'worst island')
title('QOS(generation) for 0% migration')

subplot(5,2,6);

plot(X2, islandsRef(:, X2));
title('QOS(generation) for 0% migration')

subplot(5,2,7);

plot(X1, islandsCirc(:, X1));
title('QOS(generation) for 50% circular migration')

subplot(5,2,8);

plot(X2, islandsCirc(:, X2));
title('QOS(generation) for 50% circular migration')

subplot(5,2,9);

plot(X1, islandsRand(:, X1));
title('QOS(generation) for 50% random migration')

subplot(5,2,10);

plot(X2, islandsRand(:, X2));
title('QOS(generation) for 50% random migration')
