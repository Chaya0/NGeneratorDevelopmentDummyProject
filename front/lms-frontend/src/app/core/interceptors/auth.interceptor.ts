import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { catchError, switchMap } from "rxjs/operators";
import { throwError } from "rxjs";
import { inject } from "@angular/core";
import { AuthService } from "../services/auth.service";
import { ToastService } from "../services/toast.service";
import { Router } from '@angular/router';
import { SearchService } from '../services/search.service';
import { TranslationService } from '../services/translation.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const toastService = inject(ToastService);
  const translationService = inject(TranslationService);
  const router = inject(Router)
  const searchService = inject(SearchService)

  if (req.url.endsWith('/login') || req.url.endsWith('/refresh-token') || req.url.endsWith('/logout') || req.url.endsWith('/validate-session')) {
    return next(req);
  }
  const authReq = req.clone({
    withCredentials: true,
  });

  // Handle the request
  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = '';
      if (error.error instanceof ErrorEvent) {
        // Client-side error
        errorMessage = `Client Error: ${error.error.message}`;
      } else {
        errorMessage = `Server Error: ${error.status}\nMessage: ${error.message}`;
        switch (error.status) {
          case 400:
            if(error.error.localizationKey) {
              let attr = error.error.details?.entity + "." + error.error.details?.attribute;
              console.log(translationService.translate(attr))
              errorMessage = (attr ? translationService.translate(attr) : "") + " " + translationService.translate(error.error.localizationKey)
              toastService.showError(errorMessage)
              break;
            }
            errorMessage = 'Bad Request: The server could not understand the request.';
            toastService.showError(error.error.message ? error.error.message : errorMessage)
            break;
          case 401:
            return authService.refreshToken().pipe(
              switchMap(() => {
                // Update the request with the new token
                const newAuthReq = req.clone({
                  withCredentials: true,
                  // headers: req.headers.set('Authorization', `Bearer ${newAuthToken}`)
                });
                return next(newAuthReq);
              }),
              catchError((refreshError) => {
                console.log(refreshError)
                authService.logout(false);
                if(error.error.localizationKey) {
                  errorMessage = translationService.translate(error.error.localizationKey)
                }else{
                  errorMessage = 'Session expired. Please log in again.';
                }
                router.navigate(['/login']);
                // window.location.reload();
                return throwError(() => new Error(errorMessage));
              })
            );
          case 403:
            if(error.error.localizationKey) {
              errorMessage = translationService.translate(error.error.localizationKey)
              toastService.showError(errorMessage)
              break;
            }
            errorMessage = 'Forbidden: You do not have the necessary permissions to access this resource.';
            toastService.showError(error.error.message ? error.error.message : errorMessage)
            break;
          case 404:
            if(error.error.localizationKey) {
              errorMessage = translationService.translate(error.error.localizationKey)
              toastService.showError(errorMessage)
              break;
            }
            errorMessage = 'Not Found: The requested resource could not be found.';
            toastService.showError(error.error.message ? error.error.message : errorMessage)
            router.navigate(['/']); // Redirect to a not found page
            break;
          case 500:
            if(error.error.localizationKey) {
              errorMessage = translationService.translate(error.error.localizationKey)
              toastService.showError(errorMessage)
              break;
            }
            errorMessage = 'Internal Server Error: An unexpected error occurred on the server.';
            console.log(error)
            toastService.showError(error.error.message ? error.error.message : errorMessage)
            break;
          default:
            if(error.error.localizationKey) {
              errorMessage = translationService.translate(error.error.localizationKey)
              toastService.showError(errorMessage)
              break;
            }
            errorMessage = `Unexpected Error: ${error.message}`;
            toastService.showError(error.error.message ? error.error.message : errorMessage)
        }
        // if (error.status === 401) {
        //   // Try to refresh the token
        //   return authService.refreshToken().pipe(
        //     switchMap(() => {
        //       // Update the request with the new token
        //       const newAuthReq = req.clone({
        //         withCredentials: true,
        //         // headers: req.headers.set('Authorization', `Bearer ${newAuthToken}`)
        //       });
        //       return next(newAuthReq);
        //     }),
        //     catchError((refreshError) => {
        //       console.log(refreshError)
        //       // If refresh also fails, log the user out and show an error
        //       // window.location.reload();
        //       authService.logout();
        //       toastService.showInfo('Session expired. Please log in again.');
        //       console.log(refreshError)
        //       window.location.reload();
        //       return throwError(refreshError);
        //     })
        //   );
        // }
      }
      searchService.emitSearchEvent(false);
      return throwError(() => new Error(errorMessage));
    })
  );
};
