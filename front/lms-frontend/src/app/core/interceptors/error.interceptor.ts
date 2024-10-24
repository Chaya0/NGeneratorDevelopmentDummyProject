import {HttpErrorResponse, HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {Router} from '@angular/router';
import {catchError, throwError} from 'rxjs';
import {ToastService} from "../services/toast.service";
import { SearchService } from '../services/search.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router)
  const toastService = inject(ToastService)
  const searchService = inject(SearchService)
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = '';
      if (error.error instanceof ErrorEvent ) {
        // Client-side error
        errorMessage = `Client Error: ${error.error.message}`;
      } else {
        // Server-side error
        errorMessage = `Server Error: ${error.status}\nMessage: ${error.message}`;

        // Optionally handle specific HTTP errors
        switch (error.status) {
          case 400:
            errorMessage = 'Bad Request: The server could not understand the request.';
            toastService.showError(errorMessage)
            break;
          case 401:
            return throwError(() => new Error());
          case 403:
            errorMessage = 'Forbidden: You do not have the necessary permissions to access this resource.';
            toastService.showError(errorMessage)
            break;
          case 404:
            errorMessage = 'Not Found: The requested resource could not be found.';
            toastService.showError(errorMessage)
            router.navigate(['/']); // Redirect to a not found page
            break;
          case 500:
            errorMessage = 'Internal Server Error: An unexpected error occurred on the server.';
            toastService.showError(errorMessage)
            break;
          default:
            errorMessage = `Unexpected Error: ${error.message}`;

            toastService.showError(errorMessage)
        }
      }// Optionally, display the error message in the UI
      searchService.emitSearchEvent(false);
      // alert(errorMessage);
      

      return throwError(() => new Error(errorMessage));
    })
  )
};
