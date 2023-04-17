package com.example.coursework.Service;

import com.example.coursework.model.Color;
import com.example.coursework.model.Size;
import com.example.coursework.model.Socks;

public interface SocksService {

    Socks addSocks(Socks socks, long quantity);

    Socks editSocks(Socks socks, long quantity);

    long getSocksNumByParam(Color color, Size size, int cottonMin, int cottonMax);

    boolean deleteSocks(Socks socks, long quantity);
}