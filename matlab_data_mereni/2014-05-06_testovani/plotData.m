function plotData
    % run this function to connect and plot raw EEG data
    % make sure to change portnum1 to the appropriate COM port

    % vytvari promenne data ve vychozi workspace



    data = evalin('base','data');
    loops = size(data,1);
    width = 512;
    
    treshold = 250;
    tolerance = 15;
    buffer = 32;
    triggerMouseClick = false;

    %%
    % plot data

    i = buffer + 1;

    while (i < loops)   %loop for 20 seconds

        blinked = analyse(data, i - buffer, buffer, treshold, tolerance, triggerMouseClick);
        
        if(blinked)
            data(i-buffer:i,2) = 1700;
        end
        
        plotRAW(data, i, width); % plot the data, update every .5 seconds (256 points)

        i = i + buffer;

        pause(0.05);
    end



end