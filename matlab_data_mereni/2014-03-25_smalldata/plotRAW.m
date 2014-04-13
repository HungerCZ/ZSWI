function plotRAW(data, offset, width)
%this subfunction is used to plot EEG data

height = 2000;

new = data(1+offset:width+offset,1:1);

plot(new);
axis([0 width -height height]);
drawnow;
