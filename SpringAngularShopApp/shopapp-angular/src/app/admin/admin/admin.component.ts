import { Component, OnInit } from '@angular/core';
import { UserResponse } from '../../responses/user/user.reponse';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent implements OnInit {

  ngOnInit(): void {
    
  }
  
}
