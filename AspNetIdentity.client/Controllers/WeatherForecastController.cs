using AspNetIdentity.client.Utilities;
using AspNetIdentityDemo.Shared;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;

namespace AspNetIdentity.client.Controllers
{
    public class WeatherForecastController : Controller
    {

        public async Task<IActionResult> Index()
        {
            IEnumerable<WeatherForecast> forecasts = new List<WeatherForecast>();
  
            if (HttpContext.Request.Cookies[Constants.XAccessToken] == null)
            {
                ViewBag.message = "No";
            }
            else
            {
                //ViewBag.message = HttpContext.Request.Cookies[Constants.XAccessToken];

                HttpClient httpClient = new HttpClient();
                httpClient.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", HttpContext.Request.Cookies[Constants.XAccessToken]);
                var response = await httpClient.GetAsync("http://localhost:65135/weatherforecast");
                var responseBody = await response.Content.ReadAsStringAsync();
                forecasts = JsonConvert.DeserializeObject<IEnumerable<WeatherForecast>>(responseBody);
            }
            
            return View(forecasts);
        }
    }
}
