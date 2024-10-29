import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { OrderComponent } from './components/order/order.component';
import { OrderConfirmationComponent } from './components/order-confirmation/order-confirmation.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import TokenInterceptor from './interceptors/token.interceptor';
import { AppComponent } from './app/app.component';
import { UpdateUserComponent } from './components/update-user/update-user.component';
import { AdminModule } from './admin/admin.module';

@NgModule({
  declarations: [
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    ProductDetailComponent,
    OrderComponent,
    OrderConfirmationComponent,
    LoginComponent,
    RegisterComponent,
    AppComponent,
    UpdateUserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AdminModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
