function plotData
    % run this function to connect and plot raw EEG data
    % make sure to change portnum1 to the appropriate COM port

    % vytvari promenne data ve vychozi workspace



    data = evalin('base','data');
    loops = size(data,1);
    width = 512;

    %% zobrazi dcely graf najednou
    plotRAW(data, 0, width);
    return;

    %%
    % record data

    i = width;
    offset = 0;

    skip = 16;

    while (i < loops)   %loop for 20 seconds
        i = i + skip;

        plotRAW(data, offset, width); % plot the data, update every .5 seconds (256 points)
        analyse(data, width + offset - skip, skip, 420);

        offset = offset + skip;
        pause(0.01);
    end



end