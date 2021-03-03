package com.linn.web;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Controller
public class ImageController {

    @Autowired
    OAuth2AuthorizedClientService clientService;
    @Autowired
    ClientRegistrationRepository registrationRepository;
    ///AuthenticationManager manager;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value = "/singin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity single(@RequestBody  TokenModel data, HttpServletRequest request) throws GeneralSecurityException, IOException {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        HttpSession session= request.getSession();
        UserPrincipal principal=new UserPrincipal();
        ClientRegistration r=registrationRepository.findByRegistrationId("google");
        //OAuth2AuthorizedClient client = clientService.loadAuthorizedClient("google", principal.getName());
        OAuth2AuthenticationToken token=new OAuth2AuthenticationToken(principal, principal.getAuthorities(), "google");
        //System.out.println("TOKEN " + token.getPrincipal());
        WebAuthenticationDetails auth=new WebAuthenticationDetails(request);
        token.setDetails(auth);
        System.out.println("REEEE " +request.getSession(true).getId());
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(token);

        //OAuth2AuthenticationToken token2=(OAuth2AuthenticationToken)request.getUserPrincipal();
       // System.out.println("REEEE "+ authentication.getPrincipal().getClass());
       // System.out.println("REEEE "+ request.getRequestedSessionId());        //System.out.println ("REEEE " + request.getHeaderNames().asIterator().next());
        GoogleIdToken idToken = DriveService.verify(data.getIdToken());
        System.out.println(data.getIdToken());
        idToken.getPayload().getAccessTokenHash();

        System.out.println(idToken.getClass());
     //   System.out.println ("REEEE " + request.getUserPrincipal());


        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
    @RequestMapping(value = "/single", method = RequestMethod.GET)
    public String single(Model model){
        model.addAttribute("imageUpload", new ImageUploadModel());
        return "sin";
    }
    @RequestMapping(value = "/single", method = RequestMethod.POST)
    public String singleUpload(@Valid @ModelAttribute("imageUpload") ImageUploadModel imageUploadModel,
                               BindingResult bindingResult, HttpServletRequest request, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "sin";
        }
        OAuth2AuthenticationToken token=(OAuth2AuthenticationToken)request.getUserPrincipal();
        System.out.println(token.getPrincipal().getAttribute("email").toString());
        Image image =new Image();
        image.setHeading(imageUploadModel.getHeading());
        String encodedString = Base64.getEncoder().encodeToString(imageUploadModel.getFile().getBytes());
        System.out.println(imageUploadModel.getFile().getBytes().length);
        image.setImage(Base64Utils.encodeToString(imageUploadModel.getFile().getBytes()));
        Optional<User> userOptional=userRepository.findByEmail(token.getPrincipal().getAttribute("email"));
        User user=userOptional.get();
        image.setUser(user);

        Image i= imageRepository.save(image);

        Optional<Image> a=imageRepository.findById((long) 1);
        model.addAttribute("imageUpload", new ImageUploadModel());
        return "redirect:/imageList";
    }
    @RequestMapping(value = "/imageList", method = RequestMethod.GET)
    public String username(Authentication authentication, HttpServletRequest request,Model model){
        OAuth2AuthenticationToken token=(OAuth2AuthenticationToken)request.getUserPrincipal();
        System.out.println( token.getPrincipal());
        List<Image> imageList=imageRepository.findAllByUserEmail(token.getPrincipal()
               .getAttribute("email").toString());
       // System.out.println(imageList.size());
       model.addAttribute("images",imageList);
        System.out.println ("REEEE " + request.getUserPrincipal().getName().getClass());
        System.out.println ("REEEE " + request.getAuthType());
        return "images";

    }
    @RequestMapping(value = "/drive/base64/image/upload", method = RequestMethod.POST)
    public ResponseEntity uploadToDrive(@RequestBody  ImageBase64Model data,@RequestHeader(value = "authorization") String authorization, Authentication authentication, HttpServletRequest request, Model model) throws IOException {
        OAuth2AuthenticationToken token=(OAuth2AuthenticationToken)request.getUserPrincipal();
        OAuth2AuthorizedClient client = clientService
                .loadAuthorizedClient(
                        token.getAuthorizedClientRegistrationId(),
                        token.getName());
        //Image image=imageRepository.findById(1L).get();
        Drive drive =DriveService.get(authorization);
       // DefaultOidcUser user=(DefaultOidcUser) authentication.getDetails().getClass();
        //System.out.println();

        System.out.println(authorization);
        System.out.println("DATA: "+data.getFilename());
        System.out.println("DATA: "+data.getImage64());

        //System.out.println(authentication);
       // System.out.println(authentication.getPrincipal().getClass());
       // System.out.println(user.getIdToken());
        request.getHeaderNames().asIterator().forEachRemaining(System.out::println);
        //Authentication authentication = new OAuth2LoginAuthenticationToken();th2(principal, principal.getPassword(), principal.getAuthorities());
        //System.out.println("REEEE " +request.getUserPrincipal());
       // System.out.println("REEEE " +request.getSession(true).getId());
        //System.out.println("REEEE " +request.getRequestedSessionId());
       // Authentication authentication =
               // SecurityContextHolder
                    //    .getContext()
                     //   .getAuthentication();
        byte[] imageData=Base64.getDecoder().decode(data.getImage64());
        InputStream is = new ByteArrayInputStream(imageData);
        //InputStream a=new BufferedInputStream(is);
        File file = new File();
        file.setName(data.getFilename()+".jpg");
        InputStreamContent content = new InputStreamContent("image/jpeg", is);
        File uploadedFile = drive.files().create(file, content).setFields("id").execute();
        System.out.println(uploadedFile.getId());
        return new ResponseEntity<>("OK", HttpStatus.OK);


    }
    @RequestMapping(path ="/googledrive/upload/{id}/name/{filename}",method = RequestMethod.POST)
    public ResponseEntity<?> UpdateImagePublish(@PathVariable("id") Long id, @PathVariable("filename") String filename,
                                                Authentication authentication, HttpServletRequest request, Model model) throws IOException {
        System.out.println(id);
        System.out.println(filename);
        OAuth2AuthenticationToken token=(OAuth2AuthenticationToken)request.getUserPrincipal();
        OAuth2AuthorizedClient client = clientService
                .loadAuthorizedClient(
                        token.getAuthorizedClientRegistrationId(),
                        token.getName());
        Drive drive =DriveService.get(client.getAccessToken().getTokenValue());
        Image image=imageRepository.findById(id).get();
        byte[] imageData=Base64.getDecoder().decode(image.getImage());
        InputStream is = new ByteArrayInputStream(imageData);
        File file = new File();
        file.setName(filename+".jpg");
        InputStreamContent content = new InputStreamContent("image/jpeg", is);
        File uploadedFile = drive.files().create(file, content).setFields("id").execute();
        System.out.println(uploadedFile.getId());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
