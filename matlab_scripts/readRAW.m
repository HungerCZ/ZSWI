function readRAW
% run this function to connect and plot raw EEG data
% make sure to change portnum1 to the appropriate COM port

% vytvari promenne data ve vychozi workspace


loops = 61440;
width = 512;
data = zeros(loops,3);    %preallocate buffer

treshold = 250;
tolerance = 15;
buffer = 64;

portnum1 = 18;   %COM Port #
comPortName1 = sprintf('\\\\.\\COM%d', portnum1);


% Baud rate for use with TG_Connect() and TG_SetBaudrate().
TG_BAUD_57600 = 57600;


% Data format for use with TG_Connect() and TG_SetDataFormat().
TG_STREAM_PACKETS = 0;


% Data type that can be requested from TG_GetValue().
TG_DATA_BATTERY           =  0;
TG_DATA_POOR_SIGNAL       =  1;
TG_DATA_ATTENTION         =  2;
TG_DATA_MEDITATION        =  3;
TG_DATA_RAW               =  4;
TG_DATA_DELTA             =  5;
TG_DATA_THETA             =  6;
TG_DATA_ALPHA1            =  7;
TG_DATA_ALPHA2            =  8;
TG_DATA_BETA1             =  9;
TG_DATA_BETA2             = 10;
TG_DATA_GAMMA1            = 11;
TG_DATA_GAMMA2            = 12;
TG_DATA_BLINK_STRENGTH    = 37;
TG_DATA_READYZONE         = 38;


%load thinkgear dll
loadlibrary('Thinkgear.dll');
fprintf('Thinkgear.dll loaded\n');

%get dll version
dllVersion = calllib('Thinkgear', 'TG_GetDriverVersion');
fprintf('ThinkGear DLL version: %d\n', dllVersion );


%%
% Get a connection ID handle to ThinkGear
connectionId1 = calllib('Thinkgear', 'TG_GetNewConnectionId');
if ( connectionId1 < 0 )
    error( 'ERROR: TG_GetNewConnectionId() returned %d.\n', connectionId1 );
end;

%% Set/open stream (raw bytes) log file for connection
%errCode = calllib('Thinkgear', 'TG_SetStreamLog', connectionId1, 'streamLog.txt' );
%if( errCode < 0 )
%    error( 'ERROR: TG_SetStreamLog() returned %d.\n', errCode );
%end;
%
%% Set/open data (ThinkGear values) log file for connection
%errCode = calllib('Thinkgear', 'TG_SetDataLog', connectionId1, 'dataLog.txt' );
%if( errCode < 0 )
%    error( 'ERROR: TG_SetDataLog() returned %d.\n', errCode );
%end;

% Attempt to connect the connection ID handle to serial port
errCode = calllib('Thinkgear', 'TG_Connect',  connectionId1,comPortName1,TG_BAUD_57600,TG_STREAM_PACKETS );
if ( errCode < 0 )
    error( 'ERROR: TG_Connect() returned %d.\n', errCode );
end

fprintf( 'Connected.  Reading Packets...\n' );


obj = onCleanup(@()disconnect(connectionId1));

function disconnect(cnnid)
    %% disconnect
    calllib('Thinkgear', 'TG_FreeConnection', cnnid );
    disp('Cleanup');
end


%%
% record data

i = 0;
offset = 0;
c_start = clock;

while (i < loops)   %loop for 20 seconds
    if (calllib('Thinkgear','TG_ReadPackets',connectionId1,1) == 1)   %if a packet was read...
        
        if (calllib('Thinkgear','TG_GetValueStatus',connectionId1,TG_DATA_RAW) ~= 0)   %if RAW has been updated
            i = i + 1;
            if(i > width)
                offset = offset + 1;
            end
            
            data(i,1) = calllib('Thinkgear','TG_GetValue',connectionId1,TG_DATA_RAW);
            data(i,2) = 0;
            data(i,3) = 0;
            
            if(mod(i,buffer) == 0)
                %disp(i);
                
                % get quality
                if (calllib('Thinkgear','TG_GetValueStatus',connectionId1,TG_DATA_POOR_SIGNAL) ~= 0)
                    data(i,3) = calllib('Thinkgear','TG_GetValue',connectionId1,TG_DATA_POOR_SIGNAL);
                end

                % quality > 1
                if(data(i,3) == 1)
                
                    % analyse blink
                    blinked = analyse(data, i - buffer, buffer, treshold, tolerance, true);
                    if(blinked)
                        data(i-buffer:i,2) = 1700;
                    end
                else
                    fprintf('Please check position - signal quality (desired 1): %d\n', data(i,3));
                end
                
                plotRAW(data, offset, width); % plot the data
            end
            
        end
    end
end


%%
% save data

assignin('base', 'data', data);
assignin('base', 'data_start', datestr(c_start));
assignin('base', 'data_end', datestr(now));
assignin('base', 'data_treshold', treshold);
assignin('base', 'data_tolerance', tolerance);
assignin('base', 'data_buffer', buffer);




%% need to test
disconnect(connectionId1);


end