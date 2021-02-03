package com.linn.web;

import com.google.api.client.http.FileContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class ImageController {

    @Autowired
    OAuth2AuthorizedClientService clientService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;

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
        List<Image> imageList=imageRepository.findAllByUserEmail(token.getPrincipal()
                .getAttribute("email").toString());
        System.out.println(imageList.size());
        model.addAttribute("images",imageList);
        System.out.println(request.getUserPrincipal());
        return "images";

    }
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String uploadToDrive(Authentication authentication, HttpServletRequest request,Model model) throws IOException {
        OAuth2AuthenticationToken token=(OAuth2AuthenticationToken)request.getUserPrincipal();
        OAuth2AuthorizedClient client = clientService
                .loadAuthorizedClient(
                        token.getAuthorizedClientRegistrationId(),
                        token.getName());
        Image image=imageRepository.findById(1L).get();
        Drive drive =DriveService.get(client.getAccessToken().getTokenValue());

        byte[] imageData=Base64.getDecoder().decode(image.getImage());
        InputStream is = new ByteArrayInputStream(imageData);
        InputStream a=new BufferedInputStream(is);
        File file = new File();
        file.setName("profile.jpg");
        InputStreamContent content = new InputStreamContent("image/jpeg", is);
        File uploadedFile = drive.files().create(file, content).setFields("id").execute();
        System.out.println(uploadedFile.getId());
        model.addAttribute("imageUpload", new ImageUploadModel());
        return "images";

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
