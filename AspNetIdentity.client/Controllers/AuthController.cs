using AspNetIdentity.client.Utilities;
using AspNetIdentityDemo.Shared;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace AspNetIdentity.client.Controllers
{
    public class AuthController : Controller
    {
        public IActionResult Register()
        {
            return View();
        }
        [HttpPost]
        public async Task<IActionResult> Register(RegisterViewModel model )
        {
            HttpClient httpClient = new HttpClient();
            var jsonData = JsonConvert.SerializeObject(model);
            var content = new StringContent(jsonData, Encoding.UTF8, "application/json");
            var response = await httpClient.PostAsync("http://localhost:65135/api/auth/register", content);
            var responseBody = await response.Content.ReadAsStringAsync();
            var responseObject = JsonConvert.DeserializeObject<UserManagerResponse>(responseBody);
            if(responseObject.Errors != null  && responseObject.Errors.Count() > 0)
            {
                ViewBag.message = responseObject.Errors.FirstOrDefault();
            }
            else
            {
                ViewBag.message = responseObject.Message;
            }
            
                       
            return View();
        }
        public IActionResult Login()
        {
            return View();
        }
        [HttpPost]
        public async Task<IActionResult> Login(LoginViewModel model)
        {
            HttpClient httpClient = new HttpClient();
            var jsonData = JsonConvert.SerializeObject(model);
            var content = new StringContent(jsonData, Encoding.UTF8, "application/json");
            var response = await httpClient.PostAsync("http://localhost:65135/api/auth/login", content);
            var responseBody = await response.Content.ReadAsStringAsync();
            var responseObject = JsonConvert.DeserializeObject<UserManagerResponse>(responseBody);
            if (responseObject.Errors != null && responseObject.Errors.Count() > 0)
            {
                ViewBag.message = responseObject.Errors.FirstOrDefault();
            }
            else
            {
                Response.Cookies.Append(Constants.XAccessToken, responseObject.Message,new CookieOptions { 
                    HttpOnly = true,
                    SameSite = SameSiteMode.Strict
                });
                ViewBag.message = responseObject.Message;
            }


            return View();
        }
    }
}
