clear;
clc;


load ..\result\result-adj-tournament-params.txt

figure;

matrix = [];

rows = 12;
cols = 7;

vals = result_adj_tournament_params(:,3);
counter = 0;
K = [];
P = [];
for i = 0:1:rows-1
    for j = 1:1:cols
        if i == 0
            P = [P result_adj_tournament_params(1+rows*(j-1),1)];
        end
        matrix(i+1,j) = vals(j+i*cols);
    end
    
    K = [K result_adj_tournament_params(i+1,2)];
end
result_adj_tournament_params
P
K
matrix

ph = pcolor(P,K,matrix)

cd=get(ph,'cdata');
cd(1:2,1:2)=max(cd(:));
set(ph,'cdata',cd)
     
axis([0 1 0 1]);

title('Colormap of QOS vs. p and k values of tournament');

%axis equal tight 