#include <tchar.h>
#include <Windows.h>

// send left mouse button click
void LeftClick()
{  
    INPUT Input={0};
    // left down 
    Input.type = INPUT_MOUSE;
    Input.mi.dwFlags = MOUSEEVENTF_LEFTDOWN;
    ::SendInput(1,&Input,sizeof(INPUT));

    // left up
    ::ZeroMemory(&Input,sizeof(INPUT));
    Input.type = INPUT_MOUSE;
    Input.mi.dwFlags = MOUSEEVENTF_LEFTUP;
    ::SendInput(1,&Input,sizeof(INPUT));
}

// send key W
void SendW()
{  
    INPUT Input={0};

    // W down
    Input.type = INPUT_KEYBOARD;
    Input.ki.wVk = 'W';
	Input.ki.dwFlags = 0; // down
    ::SendInput(1,&Input,sizeof(INPUT));

    // W up
    ::ZeroMemory(&Input,sizeof(INPUT));
    Input.type = INPUT_KEYBOARD;
    Input.ki.wVk = 'W';
	Input.ki.dwFlags = KEYEVENTF_KEYUP; 
    ::SendInput(1,&Input,sizeof(INPUT));
}


int _tWinMain(HINSTANCE hInstance, HINSTANCE, LPTSTR lpCmdLine, int cmdShow)
{
    LeftClick();
    return 0;
}


