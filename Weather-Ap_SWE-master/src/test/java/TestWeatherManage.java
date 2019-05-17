import org.junit.jupiter.api.Test;
import weather.sample.WeatherManage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWeatherManage {
    private WeatherManage weatherManage;

    @Test
    public void testfahrenToCelsius() {
        assertEquals("10°C", WeatherManage.fahrenToCelsius(50));
        assertEquals("-17,22°C", WeatherManage.fahrenToCelsius(1));
        assertEquals("-17,78°C", WeatherManage.fahrenToCelsius(0));
        assertEquals("-23,33°C", WeatherManage.fahrenToCelsius(-10));
        assertEquals("-17,72°C", WeatherManage.fahrenToCelsius(0.1));
        assertEquals("-17,16°C", WeatherManage.fahrenToCelsius(1.111));
        assertEquals("-24,44°C", WeatherManage.fahrenToCelsius(-12.0));
        assertEquals("-12,09°C", WeatherManage.fahrenToCelsius(10.231));
    }

    @Test
    public void testfahrenToCelsius2() {
        assertEquals(10.0, WeatherManage.fahrenToCelsius2(50));
        assertEquals(-17.22, WeatherManage.fahrenToCelsius2(1));
        assertEquals(-17.78, WeatherManage.fahrenToCelsius2(0));
        assertEquals(-23.33, WeatherManage.fahrenToCelsius2(-10));
        assertEquals(-17.72, WeatherManage.fahrenToCelsius2(0.1));
        assertEquals(-17.16, WeatherManage.fahrenToCelsius2(1.111));
        assertEquals(-24.44, WeatherManage.fahrenToCelsius2(-12.0));
        assertEquals(-12.09, WeatherManage.fahrenToCelsius2(10.231));
    }
}
