function plotRAW(data, offset, width)
%% graf s posuvnikem

height = 1200;

if(size(data,1) == 1)
    loops = size(data,2);
else
    loops = size(data,1);
end

% dx is the width of the axis 'window'
plot(data);

% Set appropriate axis limits and settings
set(gcf,'doublebuffer','on');
% This avoids flickering when updating the axis
set(gca,'xlim',[0 width]);
set(gca,'ylim',[-height height]);

% Generate constants for use in uicontrol initialization
pos = get(gca,'position');
Newpos = [pos(1) pos(2)-0.1 pos(3) 0.05];
% This will create a slider which is just underneath the axis
% but still leaves room for the axis labels above the slider

S = ['set(gca,''xlim'',get(gcbo,''value'')+[0 ' num2str(width) '])'];
% Setting up callback string to modify XLim of axis (gca)
% based on the position of the slider (gcbo)

% Creating Uicontrol
slider = uicontrol('style','slider',...
                   'units','normalized','position',Newpos,...
                   'callback',S,'min',0,'max',loops-width);


set(gca,'xlim',offset + [0 width]);
set(slider,'value',offset);



return;


%% obycejny zpusob vykreslovani grafu

height = 2000;

if(size(data,1) == 1)
    new = data(1:1,1+offset:width+offset);
else
    new = data(1+offset:width+offset,1:1);
end

plot(new);
axis([0 width -height height]);
drawnow;
