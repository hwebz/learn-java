import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { OrderComponent } from './order/order.component';
import { OrderConfirmationComponent } from './order-confirmation/order-confirmation.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    ProductDetailComponent,
    OrderComponent,
    OrderConfirmationComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [LoginComponent]
})
export class AppModule { }
